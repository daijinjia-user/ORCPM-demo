# ORCPM-demo

ORCPM-demo is a web application for visualizing and analyzing datasets. It allows users to upload files, process data, and visualize results using various plots.

## Features

- Upload CSV files for data processing
- Visualize data using scatter plots
- Generate box plots for feature recommendation

## Installation

### Prerequisites

- Java 8 or later
- Apache Maven
- Python 3.6 or later
- Required Python libraries: `matplotlib`, `numpy`

### Steps

1. Clone the repository:
    ```bash
    git clone https://github.com/daijinjia-user/ORCPM-demo.git
    cd ORCPM-demo
    ```

2. Build the project using Maven:
    ```bash
    mvn clean install
    ```

3. Install the required Python libraries:
    ```bash
    pip install matplotlib numpy
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## Usage

1. Open your web browser and navigate to `http://localhost:8080`.
2. Use the form to upload a CSV file for processing.
3. The application will process the file and display visualizations based on the data.
4. Based on box plot analysis, recommended core features will be displayed. You can choose your input accordingly.

## Example

1. Upload a CSV file:
    ```
    +----------------------------+
    | Choose File                |
    +----------------------------+
    | [Upload]                   |
    +----------------------------+
    ```

2. View the visualizations and recommendations:
    ```
    Based on box plot the recommended core features have [Recommended Features], Please choose your input
    ```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a pull request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

If you have any questions or suggestions, please feel free to contact us at `example@example.com`.

