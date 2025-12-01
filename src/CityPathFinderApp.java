import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CityPathFinderApp extends Application {
    
    private Nation nation;
    private ComboBox<String> startCityCombo;
    private ComboBox<String> destCityCombo;
    private TextArea dfsResult;
    private TextArea dfsShortestResult;
    private TextArea dijkstraResult;
    private Button findPathButton;
    private ProgressIndicator progressIndicator;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the nation data
            nation = new Nation("src/Turkish_cities.csv");
        } catch (FileNotFoundException e) {
            showError("Dosya bulunamadı: " + e.getMessage());
            Platform.exit();
            return;
        }
        
        // Create UI components
        primaryStage.setTitle("Türk Şehirleri - En Kısa Yol Bulucu");
        
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Title
        Label titleLabel = new Label("Türk Şehirleri Arası En Kısa Yol Bulucu");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        titleLabel.setAlignment(Pos.CENTER);
        
        // City selection section
        HBox citySelectionBox = new HBox(15);
        citySelectionBox.setAlignment(Pos.CENTER);
        citySelectionBox.setPadding(new Insets(10));
        
        Label startLabel = new Label("Başlangıç Şehri:");
        startLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        startCityCombo = new ComboBox<>();
        startCityCombo.setPrefWidth(200);
        
        Label destLabel = new Label("Hedef Şehir:");
        destLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        destCityCombo = new ComboBox<>();
        destCityCombo.setPrefWidth(200);
        
        // Populate city combos
        String[] cityNames = Arrays.stream(nation.getCities())
                .map(City::getName)
                .sorted()
                .collect(Collectors.toList())
                .toArray(new String[0]);
        startCityCombo.getItems().addAll(cityNames);
        destCityCombo.getItems().addAll(cityNames);
        
        citySelectionBox.getChildren().addAll(startLabel, startCityCombo, destLabel, destCityCombo);
        
        // Button and progress indicator
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        
        findPathButton = new Button("Yolu Bul");
        findPathButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        findPathButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10 30;");
        findPathButton.setOnAction(e -> findPaths());
        
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(30, 30);
        
        buttonBox.getChildren().addAll(findPathButton, progressIndicator);
        
        // Results section
        Label resultsLabel = new Label("Algoritma Sonuçları");
        resultsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        resultsLabel.setTextFill(Color.web("#2c3e50"));
        
        GridPane resultsGrid = new GridPane();
        resultsGrid.setHgap(15);
        resultsGrid.setVgap(15);
        resultsGrid.setPadding(new Insets(10));
        
        // DFS Result
        VBox dfsBox = createAlgorithmBox("DFS", "Derin Öncelikli Arama\n(İlk Bulunan Yol)");
        dfsResult = new TextArea();
        dfsResult.setEditable(false);
        dfsResult.setPrefRowCount(6);
        dfsResult.setWrapText(true);
        dfsResult.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");
        dfsBox.getChildren().add(dfsResult);
        resultsGrid.add(dfsBox, 0, 0);
        
        // DFS-Shortest Result
        VBox dfsShortestBox = createAlgorithmBox("DFS-Shortest", "Derin Öncelikli Arama\n(En Kısa Yol)");
        dfsShortestResult = new TextArea();
        dfsShortestResult.setEditable(false);
        dfsShortestResult.setPrefRowCount(6);
        dfsShortestResult.setWrapText(true);
        dfsShortestResult.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");
        dfsShortestBox.getChildren().add(dfsShortestResult);
        resultsGrid.add(dfsShortestBox, 1, 0);
        
        // Dijkstra Result
        VBox dijkstraBox = createAlgorithmBox("Dijkstra", "Dijkstra Algoritması\n(En Kısa Yol)");
        dijkstraResult = new TextArea();
        dijkstraResult.setEditable(false);
        dijkstraResult.setPrefRowCount(6);
        dijkstraResult.setWrapText(true);
        dijkstraResult.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");
        dijkstraBox.getChildren().add(dijkstraResult);
        resultsGrid.add(dijkstraBox, 2, 0);
        
        // Add all components to root
        root.getChildren().addAll(
            titleLabel,
            citySelectionBox,
            buttonBox,
            resultsLabel,
            resultsGrid
        );
        
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    
    private VBox createAlgorithmBox(String title, String description) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-width: 2; -fx-border-radius: 5;");
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web("#2980b9"));
        
        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Arial", 11));
        descLabel.setTextFill(Color.web("#7f8c8d"));
        descLabel.setWrapText(true);
        
        box.getChildren().addAll(titleLabel, descLabel);
        return box;
    }
    
    private void findPaths() {
        String startCity = startCityCombo.getValue();
        String destCity = destCityCombo.getValue();
        
        if (startCity == null || destCity == null) {
            showError("Lütfen başlangıç ve hedef şehirleri seçin!");
            return;
        }
        
        if (startCity.equals(destCity)) {
            showError("Başlangıç ve hedef şehir aynı olamaz!");
            return;
        }
        
        // Disable button and show progress
        findPathButton.setDisable(true);
        progressIndicator.setVisible(true);
        
        // Clear previous results
        dfsResult.clear();
        dfsShortestResult.clear();
        dijkstraResult.clear();
        
        // Run algorithms in a separate thread to avoid blocking UI
        new Thread(() -> {
            try {
                // Run DFS
                long start = System.nanoTime();
                ShortestPathAlgorithms.Path dfsPath = ShortestPathAlgorithms.dfs(nation, startCity, destCity);
                long end = System.nanoTime();
                double dfsTimeMs = (end - start) / 1_000_000.0;
                
                // Run DFS-Shortest
                start = System.nanoTime();
                ShortestPathAlgorithms.Path dfsShortestPath = ShortestPathAlgorithms.dfsShortest(nation, startCity, destCity);
                end = System.nanoTime();
                double dfsShortestTimeMs = (end - start) / 1_000_000.0;
                
                // Run Dijkstra
                start = System.nanoTime();
                ShortestPathAlgorithms.Path dijkstraPath = ShortestPathAlgorithms.dijkstra(nation, startCity, destCity);
                end = System.nanoTime();
                double dijkstraTimeMs = (end - start) / 1_000_000.0;
                
                // Update UI on JavaFX thread
                Platform.runLater(() -> {
                    updateResult(dfsResult, dfsPath, dfsTimeMs, "DFS");
                    updateResult(dfsShortestResult, dfsShortestPath, dfsShortestTimeMs, "DFS-Shortest");
                    updateResult(dijkstraResult, dijkstraPath, dijkstraTimeMs, "Dijkstra");
                    
                    findPathButton.setDisable(false);
                    progressIndicator.setVisible(false);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showError("Hata: " + e.getMessage());
                    findPathButton.setDisable(false);
                    progressIndicator.setVisible(false);
                });
            }
        }).start();
    }
    
    private void updateResult(TextArea textArea, ShortestPathAlgorithms.Path path, double timeMs, String algorithmName) {
        StringBuilder sb = new StringBuilder();
        sb.append("Algoritma: ").append(algorithmName).append("\n");
        sb.append("─────────────────────────\n\n");
        
        if (path == null) {
            sb.append("Yol bulunamadı!\n");
            sb.append("Seçilen şehirler arasında bağlantı yok.\n");
        } else {
            sb.append("Yol:\n");
            City[] cities = path.getCities();
            for (int i = 0; i < cities.length; i++) {
                sb.append(cities[i].getName());
                if (i < cities.length - 1) {
                    sb.append(" → ");
                }
            }
            sb.append("\n\n");
            sb.append("Toplam Mesafe: ").append(String.format("%.2f", path.getTotalDistance())).append(" km\n");
            sb.append("Şehir Sayısı: ").append(cities.length).append("\n");
        }
        
        sb.append("\n");
        sb.append("Çalışma Süresi: ").append(String.format("%.3f", timeMs)).append(" ms\n");
        sb.append("(").append(String.format("%.0f", timeMs * 1_000_000)).append(" ns)");
        
        textArea.setText(sb.toString());
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hata");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

