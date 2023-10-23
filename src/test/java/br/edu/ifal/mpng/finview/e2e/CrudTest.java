package br.edu.ifal.mpng.finview.e2e;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CrudTest {
	
	private ChromeDriver browser;
	
	@BeforeAll
	public static void setUpAll() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
	}
	
	@BeforeEach
	public void setUpEach() {
		this.browser = new ChromeDriver();		
	}
	
	@AfterEach
	public void cleanUpEach() {
		this.browser.quit();
	}

	@Test
	@Order(1)
	public void deveCarregarHome() {
		browser.navigate().to("http://localhost:8080/");
		
		String pageSource = browser.getPageSource();
		Assert.assertTrue(pageSource.contains("https://lookerstudio.google.com/embed/reporting"));
	}
	
	@Test
	@Order(2)
	public void deveNavegarParaListaDeTransacoesECriarUmaTransacaoComSucesso() {
		browser.navigate().to("http://localhost:8080/lista");
		browser.findElement(By.id("nova-transacao")).click();
		browser.findElement(By.id("data")).sendKeys("21-12-2023");
		browser.findElement(By.id("valor")).sendKeys("-6.75");
		browser.findElement(By.id("descricao")).sendKeys("TesteE2ESucesso");
		browser.findElement(By.id("categoria")).sendKeys("Indefinida");
		browser.findElement(By.id("botao-enviar-transacao")).submit();
		
		String pageSource = browser.getPageSource();
		Assert.assertTrue(pageSource.contains("TesteE2ESucesso"));
	}
	
	@Test
	@Order(3)
	public void deveNavegarParaUmaTransacaoEEditarComSucesso() {
		browser.navigate().to("http://localhost:8080/lista");
		browser.findElement(By.xpath("//li[text()='TesteE2ESucesso']")).click();
		browser.findElement(By.id("descricao")).sendKeys("Editar");
		browser.findElement(By.id("botao-enviar-transacao")).submit();
		
		String pageSource = browser.getPageSource();
		Assert.assertTrue(pageSource.contains("TesteE2ESucessoEditar"));
		
	}
	
	@Test
	@Order(4)
	public void deveNavegarParaUmaTransacaoEExcluirComSucesso() {
		browser.navigate().to("http://localhost:8080/lista");
		browser.findElement(By.xpath("//li[text()='TesteE2ESucessoEditar']")).click();
		browser.findElement(By.id("botao-excluir-transacao")).submit();
		
		String pageSource = browser.getPageSource();
		Assert.assertFalse(pageSource.contains("TesteE2ESucessoEditar"));
		
	}
	
	@Test
	@Order(5)
	public void deveNavegarParaCategoriasECriarCategoriaComSucesso() {
		browser.navigate().to("http://localhost:8080/lista/categoria");
		browser.findElement(By.id("criar-categoria")).click();
		browser.findElement(By.id("nome")).sendKeys("CategoriaTeste");
		browser.findElement(By.id("enviar-categoria")).submit();
		
		String pageSource = browser.getPageSource();
		Assert.assertTrue(pageSource.contains("CategoriaTeste"));
		
	}
	
	@Test
	@Order(6)
	public void deveNavegarParaUmaCategoriaEEditarComSucesso() {
	    browser.navigate().to("http://localhost:8080/lista/categoria");

	    WebElement element = browser.findElement(By.xpath("//li[text()='CategoriaTeste']"));
	    JavascriptExecutor jse2 = (JavascriptExecutor)browser;
	    jse2.executeScript("arguments[0].click()", element);

	    browser.findElement(By.id("nome")).sendKeys("Editar");
	    browser.findElement(By.id("enviar-categoria")).submit();

	    String pageSource = browser.getPageSource();
	    Assert.assertTrue(pageSource.contains("CategoriaTesteEditar"));
	}
	
	
	@Test
	@Order(7)
	public void deveNavegarParaOrcamentosECriarOrcamentoComSucesso() {
		browser.navigate().to("http://localhost:8080/lista/orcamento");
		browser.findElement(By.id("criar-orcamento")).click();
		browser.findElement(By.id("categoria")).sendKeys("CategoriaTesteEditar");
		browser.findElement(By.id("limite")).sendKeys("-1000");
		browser.findElement(By.id("enviar-orcamento")).submit();
		
		String pageSource = browser.getPageSource();
		Assert.assertTrue(pageSource.contains("CategoriaTesteEditar"));
		
	}
	
	@Test
	@Order(8)
	public void deveNavegarParaUmOrcamentoEEditarComSucesso() {
	    browser.navigate().to("http://localhost:8080/lista/orcamento");

	    WebElement element = browser.findElement(By.xpath("//li[text()='CategoriaTesteEditar']"));
	    JavascriptExecutor jse2 = (JavascriptExecutor)browser;
	    jse2.executeScript("arguments[0].click()", element);

	    browser.findElement(By.id("limite")).sendKeys("50");
	    browser.findElement(By.id("enviar-orcamento")).submit();

	    String pageSource = browser.getPageSource();
	    Assert.assertTrue(pageSource.contains("CategoriaTesteEditar"));
	    Assert.assertTrue(pageSource.contains("-1000.01"));
	}
	
	@Test
	@Order(9)
	public void deveNavegarParaUmOrcamentoEExcluirComSucesso() {
		browser.navigate().to("http://localhost:8080/lista/orcamento");
		
		 WebElement element = browser.findElement(By.xpath("//li[text()='CategoriaTesteEditar']"));
		 JavascriptExecutor jse2 = (JavascriptExecutor)browser;
		 jse2.executeScript("arguments[0].click()", element);
		
		browser.findElement(By.id("excluir-orcamento")).submit();
		
		String pageSource = browser.getPageSource();
		Assert.assertFalse(pageSource.contains("CategoriaTesteEditar"));
		
	}
	
	@Test
	@Order(10)
	public void deveNavegarParaUmaCategoriaEExcluirComSucesso() {
		browser.navigate().to("http://localhost:8080/lista/categoria");
		
		WebElement element = browser.findElement(By.xpath("//li[text()='CategoriaTesteEditar']"));
	    JavascriptExecutor jse2 = (JavascriptExecutor)browser;
	    jse2.executeScript("arguments[0].click()", element);
		
		browser.findElement(By.id("excluir-categoria")).submit();
		
		String pageSource = browser.getPageSource();
		Assert.assertFalse(pageSource.contains("CategoriaTesteEditar"));
		
	}

}
