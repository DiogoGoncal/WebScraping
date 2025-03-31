package controller;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class WebScraping {

    public static void rasparDados() {
        // Caminho do driver
        System.setProperty("webdriver.edge.driver", "resources/msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.setCapability("se:cdp", "134");

        // Configurações do navegador para baixar PDFs diretamente
        String downloadFilepath = "resultados";
        File downloadDir = new File(downloadFilepath);

        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFilepath);  // Diretório para salvar os PDFs
        prefs.put("plugins.always_open_pdf_externally", true);

        WebDriver driver = new EdgeDriver(options);
        driver.get("https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Espera até que o botão de cookies esteja visível
        WebElement aceitarCookies = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Aceitar')]")));
        aceitarCookies.click();

        WebElement acessarPagina = driver.findElement(By.xpath("//*[@id=\"cfec435d-6921-461f-b85a-b425bc3cb4a5\"]/div/ol/li[1]/a[1]"));
        acessarPagina.click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Espera até que o primeiro PDF esteja disponível
        WebElement linkPDF1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"cfec435d-6921-461f-b85a-b425bc3cb4a5\"]/div/ol/li[1]/a[1]")));
        String pdfURL1 = linkPDF1.getAttribute("href");
        System.out.println("Link do primeiro PDF extraído: " + pdfURL1);

        WebElement linkPDF2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div[1]/main/div[2]/div/div/div/div/div[2]/div/ol/li[2]/a")));
        String pdfURL2 = linkPDF2.getAttribute("href");
        System.out.println("Link do segundo PDF extraído: " + pdfURL2);


        driver.quit();

        System.out.println("Baixando PDF");
        PDFDownloader.downloadPDF(pdfURL1, downloadFilepath);
        PDFDownloader.downloadPDF(pdfURL2, downloadFilepath);



    }
}