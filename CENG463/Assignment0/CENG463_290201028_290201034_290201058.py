#!/usr/bin/env python3

import sys
from pathlib import Path
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

def identify_feature_types(df: pd.DataFrame, min_unique_for_cont: int = 15):
    categorical_features = []
    continuous_features = []

    for col in df.columns:
        n_unique = df[col].nunique(dropna=False)
        dtype = df[col].dtype

        if dtype == 'object' or dtype.name == 'category' or dtype == 'bool':
            categorical_features.append(col)
        elif np.issubdtype(dtype, np.number):
            if n_unique < min_unique_for_cont:
                categorical_features.append(col)
            else:
                continuous_features.append(col)
        else:
            continue

    return categorical_features, continuous_features

def generate_data_quality_reports(df: pd.DataFrame, output_dir: str = "."):
    output_path = Path(output_dir)
    output_path.mkdir(parents=True, exist_ok=True)

    cat_features, cont_features = identify_feature_types(df)

    # Continuous Report
    cont_records = []
    for col in cont_features:
        series = df[col]
        count = series.count()
        missing_pct = 100 * series.isna().sum() / len(series)
        cardinality = series.nunique()
        stats = series.describe(percentiles=[0.25, 0.5, 0.75])

        cont_records.append({
            "Feature": col,
            "Count": count,
            "% Miss.": round(missing_pct, 2),
            "Card.": cardinality,
            "Min.": stats["min"],
            "1st Qrt.": stats["25%"],
            "Mean": stats["mean"],
            "Median": stats["50%"],
            "3rd Qrt.": stats["75%"],
            "Max.": stats["max"],
            "Std. Dev.": series.std()
        })

    cont_df = pd.DataFrame(cont_records)
    cont_df.to_csv(output_path / "output_DQR_Continuous.csv", index=False)

    # Categorical Report
    cat_records = []
    for col in cat_features:
        series = df[col]
        count = series.count()
        missing_pct = 100 * series.isna().sum() / len(series)
        cardinality = series.nunique()

        mode_values = series.mode(dropna=False).tolist()
        mode1 = mode_values[0] if len(mode_values) > 0 else np.nan
        mode1_freq = series.value_counts(dropna=False).get(mode1, 0)
        mode1_pct = 100 * mode1_freq / len(series)

        # second most frequent
        value_counts = series.value_counts(dropna=False)
        second_mode = value_counts.index[1] if len(value_counts) > 1 else np.nan
        second_freq = value_counts.iloc[1] if len(value_counts) > 1 else np.nan
        second_pct = 100 * second_freq / len(series) if len(value_counts) > 1 else np.nan

        cat_records.append({
            "Feature": col,
            "Count": count,
            "% Miss.": round(missing_pct, 2),
            "Card.": cardinality,
            "Mode": mode1,
            "Mode Freq.": mode1_freq,
            "Mode %": round(mode1_pct, 2),
            "2nd Mode": second_mode,
            "2nd Mode Freq.": second_freq,
            "2nd Mode %": round(second_pct, 2) if not pd.isna(second_pct) else np.nan
        })

    cat_df = pd.DataFrame(cat_records)
    cat_df.to_csv(output_path / "output_DQR_Categorical.csv", index=False)

    print("Reports saved to", output_path.resolve())

def generate_feature_visualizations(df, target, categorical_features, continuous_features, viz_dir="viz_output"):
    output_path = Path(viz_dir)
    output_path.mkdir(parents=True, exist_ok=True)

    target_series = df[target]
    target_is_cat = target in categorical_features
    target_is_cont = target in continuous_features

    for feature in df.columns:
        if feature == target:
            continue  # Target

        if feature in continuous_features:
            if target_is_cont:
                # SCATTER PLOT
                plt.figure()
                plt.scatter(df[feature], df[target], alpha=0.5)
                plt.xlabel(feature)
                plt.ylabel(target)
                plt.title(f"{feature} vs {target}")
                plt.savefig(output_path / f"{feature}_vs_{target}_scatter.png")
                plt.close()

            elif target_is_cat:
                # MULTIPLE HISTOGRAMS by target class
                plt.figure()
                for label in df[target].dropna().unique():
                    subset = df[df[target] == label]
                    plt.hist(subset[feature].dropna(), bins=20, alpha=0.5, label=str(label))
                plt.xlabel(feature)
                plt.ylabel("Count")
                plt.title(f"{feature} distribution by {target}")
                plt.legend()
                plt.savefig(output_path / f"{feature}_vs_{target}_hist.png")
                plt.close()

        elif feature in categorical_features:
            if target_is_cont:
                # MULTIPLE HISTOGRAMS by feature class
                plt.figure()
                for label in df[feature].dropna().unique():
                    subset = df[df[feature] == label]
                    plt.hist(subset[target].dropna(), bins=20, alpha=0.5, label=str(label))
                plt.xlabel(target)
                plt.ylabel("Count")
                plt.title(f"{target} distribution by {feature}")
                plt.legend()
                plt.savefig(output_path / f"{feature}_vs_{target}_hist.png")
                plt.close()

            elif target_is_cat:
                # MULTIPLE BAR PLOTS
                counts = df.groupby([feature, target]).size().unstack(fill_value=0)
                counts.plot(kind="bar", stacked=False)
                plt.title(f"{feature} vs {target}")
                plt.xlabel(feature)
                plt.ylabel("Count")
                plt.legend(title=target)
                plt.tight_layout()
                plt.savefig(output_path / f"{feature}_vs_{target}_barplot.png")
                plt.close()


def main():
    if len(sys.argv) != 3:
        print(__doc__)
        sys.exit(1)

    csv_path = Path(sys.argv[1])
    out_dir = Path(sys.argv[2])
    out_dir.mkdir(parents=True, exist_ok=True)

    # READ DATA
    df = pd.read_csv(csv_path)

    # GENERATE RREPORTS
    generate_data_quality_reports(df, output_dir=out_dir)

    cat_feats, cont_feats = identify_feature_types(df)

    #TARGETS 

    targets = ["Diagnosis", "Age"]

    for tgt in targets:
        generate_feature_visualizations(df, tgt, cat_feats, cont_feats, viz_dir=out_dir)

    print(f"\n All outputs saved to '{out_dir.resolve()}'.")


if __name__ == "__main__":
    main()
