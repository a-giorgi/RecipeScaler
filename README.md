# Recipe Scaler

## Overview

Recipe Scaler is an Android application designed to assist users in scaling quantities based on their specific requirements. Whether you're managing ingredients for a recipe, materials for a project, or any other items needing adjustment, Recipe Scaler provides a seamless solution. Users can easily input their items, specify a target total, and effortlessly scale quantities accordingly.

## Features

### Item Input

- Users can add items to their list by clicking the "+" button.
- A pop-up window prompts users to input the name and quantity of each item.
- This process can be repeated until all desired items are added.

### Item Management

- Items are displayed in a list format on the main interface.
- Each item entry includes options to edit or delete.

### Total Specification

- Users can specify their desired total in a designated field at the bottom of the interface.
- The total can represent various metrics, such as quantity needed, target output, or any other relevant measure.

### Scaling Functionality

- Scaling can be initiated by pressing the "Scale to new total" button.
- Alternatively, users can utilize the slider in the Main View for automatic scaling of item quantities.

### Intelligent Functions

- Pressing the menu button in the top right corner provides options to manually input text to convert into a recipe.
  - **Intelligent Scan**: Use the phone's camera to capture and extract text using OCR.
  - **From Gallery**: Select an image from the gallery and extract text using OCR, similar to the Intelligent Scan option.
  - **Paste Text**: Manually paste text.

- Once the text is input, it will be processed by a TensorFlow Lite model that performs Named Entity Recognition (NER) to automatically identify and add the detected ingredients to the item list. Currently it works only with english language.