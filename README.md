# UI Validation with Java Selenium and GPT-4

Welcome to the UI Validation project using Java, Selenium, and GPT-4. This project aims to validate web application interfaces by leveraging the power of Selenium for browser automation and GPT-4 for intelligent validation.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- **Java JDK 17**
- **IntelliJ IDEA** (Community or Ultimate edition)
- **Maven** (installed and configured)
- **Selenium WebDriver**
- **GPT-4 API Access** (ensure you have API keys and access configured)

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/koswara-dev/selenium-ui-validation.git
   cd ui-validation-java-selenium-gpt4
   ```

2. **Open the project in IntelliJ IDEA:**
   - Launch IntelliJ IDEA.
   - Click on `File` > `Open`.
   - Navigate to the cloned repository's directory and select it.

3. **Build the project with Maven:**
   - Open the Terminal window within IntelliJ IDEA.
   - Run the following command:
     ```bash
     mvn clean install
     ```

## Setup

1. **Configure WebDriver:**
   - Download the WebDriver for the browser you intend to use (Chrome, Firefox, etc.).
   - Set the path to the WebDriver executable in your system properties or directly in the code.

2. **Configure GPT-4 API Access:**
   - Obtain your API key from OpenAI.
   - Set the API key in the project's configuration file or as an environment variable.
   - Rename file .env.example to .env
   - add your api key OpenAI GPT4

## Usage

### Running Tests

1. **Write Test Cases:**
   - Create a new test class under the `src/test/java` directory.
   - Use Selenium WebDriver to define browser interactions.
   - Integrate GPT-4 API calls for intelligent validation.

2. **Run the Tests:**
   - Right-click on the test class or method in IntelliJ IDEA and select `Run`.

---

Enjoy coding and happy testing! If you encounter any issues, feel free to open a new issue in the repository.


## Buy me a coffe

If you like this project and want to support its further development, buy me a coffee!

[![Buy Me a Coffee](https://www.buymeacoffee.com/assets/img/guidelines/download-assets-sm-1.svg)](https://www.buymeacoffee.com/kudajengke404)
