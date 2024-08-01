# ORCPM-demo

Regional  core pattern mining (RCPM) is designed to discover those core  patterns that may occur frequently only in a sub-region and to  reveal the dependency relationship between core and non-core  features by analysing the core pattern's prevalent sub-regions.  RCPM-demo  is a web application for visualizing and analysing datasets, which can be widely used in tasks such as rare resource  conservation, commercial site selection, and others that the core  feature needs to be specified.  It allows users to upload files, process data, and visualize results using various plots.



## Features

- Upload CSV files for data visualize 
- Analyse data distribution and recommend parameters
- Configure parameters and pattern mining
- Visualization result display and analysis



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

  or，You can package the entire project as a jar package and then execute 
 
   ```bash
   jave ORCPM-demo.jar
   ```



## Usage

1. Open your web browser and navigate to `http://localhost:8080`.
2. Use the form to upload a CSV file for processing.
3. The application will process the file and display visualizations based on the data.
4. Based on box plot analysis, recommended core features will be displayed. You can choose your input accordingly.



## Example

1. Upload a CSV file:
   ```
   +-------------------------------------+
   | Choose File                         |
   +-------------------------------------+
   | feature, X-coordinate, Y-coordinate |
   +-------------------------------------+
   | A, 4996.02, 2665.38	(example)     |
   +-------------------------------------+
   ```
   
    <p align="center">
      <img src="https://github.com/daijinjia-user/ORCPM-demo/blob/main/picture/1.png?raw=true" alt="1.png">
    </p>

2. View the visualizations and recommendations:

   ```
   Based on box plot the recommended core features have [Recommended Features], Please choose your
   input.
   ```

    <p align="center">
      <img src="https://github.com/daijinjia-user/ORCPM-demo/blob/main/picture/2.png?raw=true" alt="2.png">
    </p>

3. Configure parameters and visual fine-tuning:

   ```
   You can adjust the region partitioning by changing the core feature, pd, and core nearest
   affiliation num.
   ```
   
    <p align="center">
      <img src="https://github.com/daijinjia-user/ORCPM-demo/blob/main/picture/3.png?raw=true" alt="3.png">
    </p>

4. Submit prevalence threshold mining for Regional Core Pattern:

   ```
   Input the prevalence threshold (usually between 0 and 1), and after submission, mine the
   Regional Core Pattern. Display all patterns in a list.
   ```
    <p align="center">
      <img src="https://github.com/daijinjia-user/ORCPM-demo/blob/main/picture/4.png?raw=true" alt="4.png">
    </p>
5. Display the prevalence regions of the Regional Core Patterns:

   ```
   First, select the desired pattern from the dropdown menu. Then, you can choose specific regions
   to display a more detailed view of each area within that pattern.
   ```
* Select pattern:
   
  <p align="center">
      <img src="https://github.com/daijinjia-user/ORCPM-demo/blob/main/picture/5.png?raw=true" alt="5.png">
    </p>

* Select region:

  <p align="center">
   <img src="https://github.com/daijinjia-user/ORCPM-demo/blob/main/picture/6.png?raw=true" alt="6.png">
  </p>

