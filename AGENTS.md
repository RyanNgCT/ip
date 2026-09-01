# Project context

This repository is a starter template for a greenfield Java project used in an introductory software engineering course in an undergraduate computer science program. Students use it as the starting point for their own projects.

# Default user context

Unless the user says otherwise, assume that you are assisting a student working on a project in this repository. If the user identifies themselves as an instructor or another project stakeholder, adapt your response to that role.

# Student profile

* Prior knowledge: Basic Java and OOP concepts.
* Level of programming experience: Beginner / Intermediate
* IDE and level of expertise: Intellij IDEA (Beginner)

# Guidance for interacting with users

* Explain the rationale for significant actions: what you did and why.
* Keep explanations brief but instructive, supporting learning through responsible use of AI. For example:

  * When suggesting a Git command, briefly explain what it does.
  * Do not add, remove, or modify Javadoc comments during coding-standard cleanup; Javadoc is maintained on a separate branch.
  * Make generated code as self-explanatory as possible, and include explanatory comments where they improve understanding.
  * When faced with a design choice, choose the simplest option that is sufficient for the requirements, while briefly explaining relevant more advanced alternatives.

# Project-specific requirements

## Java version:

Ensure that Java 25 is used when running the application or build tasks. On macOS, use `sdk use java 25.0.3.fx-zulu` to switch to Java 25 if needed.

## Java coding standard:

For every Java file created or modified in this repository, read and follow the project-specific
`.codex/skills/seedu-java-coding-standard/SKILL.md` skill. This requirement applies to both production
code and test code; do not introduce a style exception unless the user explicitly authorizes it.
The skill deliberately excludes Javadoc changes.

## Git

Use lightweight tags unless the user requests an annotated tag.
When proposing or creating a commit message, include enough detail to explain the rationale for the change.
Do not commit or push unless explicitly asked.

# Other rules
Unless the user says otherwise, always ask for permission before performing any modification or writing to files or code.
