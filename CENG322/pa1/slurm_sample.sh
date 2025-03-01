#!/bin/bash
#SBATCH -A hsaat                # account / proje adi (Proje adı, projelerim komutu ile iş verilebileceğini gördüğünüz bir proje olmalıdır.)
#SBATCH -p defq                 # kuyruk (partition/queue) adi (kuyruk adı, bosmakinalar komutu ile müsait olduğunu gördüğünüz bir kuyruk olmalıdır.)
#SBATCH -n 1                    # toplam cekirdek / islemci sayisi
#SBATCH -N 1    		# # toplam makina sayisi

./compute.sh
