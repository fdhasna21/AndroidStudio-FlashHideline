<div align="center">

  <img src="app/src/main/res/drawable/ic_bg_blue.png" alt="Flash Hideline Logo" width="100"/>

  # Flash Hideline

  **A modern, lightweight Android news app delivering real-time global headlines.**

  | Category Screen | News Source & Search | Article List | Article Detail |
  | :---: | :---: | :---: | :---: |
  | <img src="https://github.com/user-attachments/assets/ec634ebc-38f4-4bb2-8507-de6176efe380" width="180" alt="Category Screen Light" /> | <img src="https://github.com/user-attachments/assets/f8748e0a-d5c8-4383-9b24-16aa86d86899" width="180" alt="Search Screen Light" /> | <img src="https://github.com/user-attachments/assets/f8231c21-4277-4697-8a1c-4dc32dd3f674" width="180" alt="Article List Light" /> | <img src="https://github.com/user-attachments/assets/89f6ea6f-831a-41a2-bb79-94127122df0f" width="180" alt="Article Detail Light" /> |
  | <img src="https://github.com/user-attachments/assets/09f379d7-1d05-4860-9884-05d59b0f9500" width="180" alt="Category Screen Dark" /> | <img src="https://github.com/user-attachments/assets/d42fd20d-6d38-4078-91f1-d6cd73f0cbd8" width="180" alt="Search Screen Dark" /> | <img src="https://github.com/user-attachments/assets/2d111086-ecec-4ab4-84c8-a6562ea9af54" width="180" alt="Article List Dark" /> | <img src="https://github.com/user-attachments/assets/b0e2b253-792d-4ee2-ad69-6cc6512addcd" width="180" alt="Article Detail Dark" /> |

</div>

---

## 🎨 Design System & Branding

* **Character:** Fast, modern, and direct to the point.
* **Palette:**
  * **Primary (Deep Slate):** `#1E293B`
  * **Accent (Electric Amber/Yellow):** `#F59E0B` *(represents "flash / speed")*
  * **Background:** `#FAFAFA`
  * **Text:** `#18181B`

---

## 📌 About & Key Features

**Flash Hideline** is powered by the **[NewsAPI.org](https://newsapi.org)** API, built to provide a clean and fast news-browsing experience.

* **Category & Source Filtering:** Browse news by category (Business, Tech, Sports, etc.) and filter by trusted publishers.
* **Smart Search & Pagination:** Debounced search with inline loading indicators and seamless infinite scrolling.
* **Article Reader:** Detailed article previews with direct links to full news stories.

---

## 🛠️ Tech Stack & Architecture

* **UI Framework:** **Jetpack Compose** (Declarative UI, Material 3, Custom Composables)
* **Architecture:** MVVM + Clean Architecture, Single Activity Pattern
* **State & Async:** StateFlow / SharedFlow, Kotlin Coroutines, Channel (UiEffect)
* **Dependency Injection:** Hilt
* **Networking:** Retrofit2, OkHttp3 (with Logging Interceptor)
* **Navigation & Image:** Navigation Compose, Coil
* **Compatibility:** Min SDK 24 (Android 7.0+)

---

## 💡 Why This Architecture?

Transitioning to **Jetpack Compose + MVVM** for this project was a deliberate challenge. Having spent 5 years specializing in XML and MVC, I built this application within a tight deadline to demonstrate my ability to quickly adapt to modern Android standards and existing codebase requirements.

While adopting declarative UI was a new paradigm for me, this project serves as proof of my technical adaptability—showing that strong core mobile engineering fundamentals allow for fast technology adoption and continuous growth.
