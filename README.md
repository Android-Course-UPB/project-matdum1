# 💱 Currency Converter

A modern Android app for real-time currency conversion, built with Jetpack Compose and following MVVM architecture.

Author: [Matei Dumitru](https://github.com/mateidumitru1)

## ✨ Features

- **Real-time conversion** between 150+ currencies
- **Personalization**:
    - Set your name
    - Choose default currencies
    - Select theme (Light/Dark)
- **Offline support** with cached exchange rates
- **Modern UI** with Jetpack Compose and Material 3
- **API Integration** with [exchangerate-api.com](https://www.exchangerate-api.com/)

## 🛠️ Tech Stack

- **Architecture**: MVVM
- **UI**: Jetpack Compose
- **Database**: Room
- **Networking**: Retrofit
- **DI**: Hilt
- **Async**: Coroutines + Flow

## 🚀 Future work

| Feature                  | Description |
|--------------------------|-------------|
| Automated Daily Updates  | ⏰ Background updates via WorkManager |
| Conversion History       | 📜 Track past conversions with timestamps |
| Interactive Charts       | 📈 Visualize currency trends with MPAndroidChart |
| Rate Alerts              | 🔔 Push notifications when target rates are hit |
| Home Screen Widget       | 🏠 Quick-access 1x1 conversion widget |