# Contributing to E-Commerce API

Thank you for considering contributing to this project! We welcome improvements, suggestions, and bug fixes. To ensure a smooth collaboration, please follow these guidelines:

## Code of Conduct

Please review and adhere to our [Code of Conduct](CODE_OF_CONDUCT.md) to maintain a respectful and inclusive environment.

## How to Contribute

1. **Fork the repository**

    * Click the **Fork** button at the top-right of the repo.
2. **Clone your fork**

   ```bash
   git clone https://github.com/your-username/your-repo.git
   cd your-repo
   ```
3. **Create a topic branch**

   ```bash
   git checkout -b feature/your-feature-name
   ```
4. **Make changes**

    * Follow existing code style and naming conventions.
    * Write clear, concise commit messages.
    * Ensure all new code includes unit tests where applicable.
5. **Run tests**

   ```bash
   ./gradlew test
   ```
6. **Commit and push**

   ```bash
   git add .
   git commit -m "Brief description of your change"
   git push origin feature/your-feature-name
   ```
7. **Open a Pull Request**

    * Navigate to your fork on GitHub.
    * Click **Compare & pull request**.
    * Provide a descriptive title and summary of your changes.
    * Reference any related issues (e.g., `Closes #123`).

## Branching Strategy

We use the following branching model:

* `main`: Stable, production-ready code.
* `develop`: Integration branch for features and fixes.
* `feature/*`: Short-lived branches for individual features.
* `hotfix/*`: Patches for urgent fixes directly on `main`.

## Code Style

* **Java**: Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
* **Formatting**: Use 4 spaces for indentation, max line length 120 characters.
* **Annotations**: Use Lombok annotations for boilerplate reduction where appropriate.

## Testing

* Write unit tests for service and mapper layers.
* Use JUnit 5 and Mockito for mocking.
* Aim for at least 80% code coverage.

## Documentation

* Update `README.md` for any CLI or configuration changes.
* Document new endpoints in the API documentation section.

## Reporting Bugs

1. Check existing [Issues](https://github.com/piotr-grosicki/project-jdp-2505-01/issues).
2. Open a new issue if necessary, including:

    * A clear title and description.
    * Steps to reproduce.
    * Expected and actual behavior.

## Requesting Features

* Open an issue with the **feature request** label.
* Provide context, use cases, and examples if possible.

---

Thank you for helping make this project better!
