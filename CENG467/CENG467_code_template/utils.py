# Group No: G12
# Authors: Ahmet Kurt 290201034 | Bilgin Baran Sezer 290201057

import torch

def predict_model(model, tokenizer, messages, configuration=None):
    if configuration is None:
        configuration = {"temperature": 0.1, "max_token_limit": 2000}

    try:
        prompt = tokenizer.apply_chat_template(messages, tokenize=False, add_generation_prompt=True)
    except Exception:
        prompt = "\n".join([f"{m.get('role','user')}: {m.get('content','')}" for m in messages])

    inputs = tokenizer(prompt, return_tensors="pt")

    inputs = {k: (v.to(model.device) if hasattr(v, "to") else v) for k, v in inputs.items()}

    gen_kwargs = {
        "max_new_tokens": int(configuration.get("max_token_limit", 2000)),
        "temperature": float(configuration.get("temperature", 0.1)),
        "do_sample": True,
        "pad_token_id": tokenizer.pad_token_id if tokenizer.pad_token_id is not None else tokenizer.eos_token_id,
        "eos_token_id": tokenizer.eos_token_id,
    }

    output_ids = model.generate(**inputs, **gen_kwargs)

    input_len = inputs["input_ids"].shape[-1]
    new_tokens = output_ids[0][input_len:]
    text = tokenizer.decode(new_tokens, skip_special_tokens=True)

    return text.strip()


def model_evaluation(model_type, model, tokenizer, system_content, question, formatted_options, configuration=None):
    if model_type == "qwen2" or model_type == "qwen3":
        messages = [
            {"role": "system", "content": system_content},
            {"role": "user", "content": f"Question: {question}\n\nOptions:\n{formatted_options}"}
        ]
        model_result = predict_model(model, tokenizer, messages, configuration)
    else: 
        raise ValueError(f"Unknown model_type: {model_type}")

    #  print(f"Model result: {model_result}")
    return model_result
