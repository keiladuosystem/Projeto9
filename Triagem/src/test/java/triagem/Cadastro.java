package triagem;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Parameterized.class) // classe que lê dados
public class Cadastro {

	// 3.2 - Atributos (caracteristicas)
	String url1;
	WebDriver driver;

	String cod;
	String resp;
	String nome;
	String cpf;
	String dtnasc;
	String sexo;
	String cep;
	String celular;
	String email;
	String confirmeemail;
	String febre;
	String faltadear;
	String tosse;
	String malestar;
	String garganta;
	String coriza;
	String dias;
	String cancer;
	String diabetes;
	String cardiaca;
	String respiratoria;
	String renal;
	String pressao;
	String transplante;
	String desconheco;
	String login;
	String senha;
	String laudo;

//Construtor
	public Cadastro(String cod, String resp, String nome, String cpf, String dtnasc, String sexo, String cep, String celular,
			String email, String confirmeemail, String febre, String faltadear, String tosse, String malestar,
			String garganta, String coriza, String dias, String cancer, String diabetes, String cardiaca,
			String respiratoria, String renal, String pressao, String transplante, String desconheco, String login,
			String senha, String laudo) {
		
		this.cod = cod;
		this.resp = resp;
		this.nome = nome;
		this.cpf = cpf;
		this.dtnasc = dtnasc;
		this.sexo = sexo;
		this.cep = cep;
		this.celular = celular;
		this.email = email;
		this.confirmeemail = confirmeemail;
		this.febre = febre;
		this.faltadear = faltadear;
		this.tosse = tosse;
		this.malestar = malestar;
		this.garganta = garganta;
		this.coriza = coriza;
		this.dias = dias;
		this.cancer = cancer;
		this.diabetes = diabetes;
		this.cardiaca = cardiaca;
		this.respiratoria = respiratoria;
		this.renal = renal;
		this.pressao = pressao;
		this.transplante = transplante;
		this.desconheco = desconheco;
		this.login = login;
		this.senha = senha;
		this.laudo = laudo;
	}

	@Parameters
	public static Collection<String[]> LerCSV() throws IOException {
		String cwd = System.getProperty("user.dir");
		System.out.println(cwd);
		return LerArquivo(cwd+"/ db/massaDados.csv");

	}

	public static Collection<String[]> LerArquivo(String caminhoMassa) throws IOException {
		List<String[]> dados = new ArrayList<String[]>();
		String linha;
		BufferedReader arquivo = new BufferedReader(new FileReader(caminhoMassa));

		while ((linha = arquivo.readLine()) != null) {
			String campos[] = linha.split(";");
			dados.add(campos);
		}
		arquivo.close();

		return dados;

	}

	public String waitForWindow(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Set<String> whNow = driver.getWindowHandles();
		return whNow.iterator().next();
	}

	@Before
	public void inciar() {
		url1 = "https://coronavirusportugal.duodoctor.com.br";
		String cwd = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver",
				cwd+"/driver/83/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	

	@After // Depois do teste
	public void Finalizar() {
		this.driver.quit();

	}
	static String pastaPrint = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());

	public void Print(String nomePrint) throws IOException {
		File foto = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(foto, new File ( "\\\\192.168.111.123\\Jenkins-QA\\DuoDoctor9\\Evidencia"
				+ pastaPrint + cod +"\\" + nomePrint + ".png"));
	}

	@Test // Durante o teste
	@Category(Cadastro.class)
	
	public void Buscar() throws IOException, InterruptedException {

		driver.get(url1); // abre o browser nessa URL
		Print("Acessar\\Passo 1 - Abre o Site");
		//Tela inicial Triagem
		try {
			driver.findElement(By.cssSelector(
					"body > div.intro-header.hidden-xs > div > div > div.col-sm-6.col-lg-6.banner1 > div > p > a")).click();
		} catch (Exception ex) {
			Assert.fail("Tela inicial de Triagem não carregou corretamente");
		}	
		assertEquals("Triagem Coronavírus - Ambulatório Virtual", driver.getTitle());
		Print("Cadastro\\Passo 1 - Acessa a tela de Cadastro do paciente");
		// Validar responsavel
		if (resp.equalsIgnoreCase("1")) {
			driver.findElement(By.cssSelector("#formQuestionsCheck > label > span")).click();
		}
		//Nome
		driver.findElement(By.id("nome")).sendKeys(Keys.chord(nome));
		//CPF
		driver.findElement(By.id("cpf")).sendKeys(Keys.chord(cpf));
		//Data de Nascimento
		driver.findElement(By.id("data_nascimento")).sendKeys(Keys.chord(dtnasc));
		// Validação para opção Sexo
		if (sexo.equalsIgnoreCase("F")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/div[4]/div[1]/label/span")).click();
		} else
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[1]/div[4]/div[2]/label/span")).click();
		//CEP - Se o CEP estiver fora da area em que o usuário profissional atende ele não conseguirá visualizar o paciente
		driver.findElement(By.id("cep")).sendKeys(Keys.chord(cep));
		//Celuar
		driver.findElement(By.id("telefone")).sendKeys(Keys.chord(celular));
		//E-mail
		driver.findElement(By.id("email")).sendKeys(Keys.chord(email));
		//Confirmar e-mail
		driver.findElement(By.id("confirme-email")).sendKeys(Keys.chord(confirmeemail));
		
		// SINAIS E SINTOMAS	
		if (febre.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[3]/div/div[1]/div[1]/div/label/span"))
					.click();
		}
		if (faltadear.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[3]/div/div[1]/div[2]/div/label/span"))
					.click();
		}
		if (tosse.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[3]/div/div[1]/div[3]/div/label/span"))
					.click();
		}
		if (malestar.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[3]/div/div[2]/div[1]/div/label/span"))
					.click();
		}
		if (garganta.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[3]/div/div[2]/div[2]/div/label/span"))
					.click();
		}
		if (coriza.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("//html/body/div[2]/div[2]/form/div[3]/div/div[2]/div[3]/div/label/span"))
					.click();
		}
		Print("Cadastro\\Passo 2 - Formulário preenchido 1");
		Thread.sleep(8000);
		//dias
		driver.findElement(By.name("inicio_sintoma")).sendKeys(Keys.chord(dias));
		
		// DoenÃ§as Associadas
		if (cancer.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[5]/div[1]/div/label/span")).click();
		}
		if (diabetes.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[5]/div[2]/div/label/span")).click();
		}
		if (cardiaca.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[5]/div[3]/div/label/span")).click();
		}
		if (respiratoria.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[6]/div[1]/div/label/span")).click();
		}
		if (renal.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[6]/div[2]/div/label/span")).click();
		}
		if (pressao.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[6]/div[3]/div/label/span")).click();
		}
		if (transplante.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[7]/div/div/label/span")).click();
		}
		if (desconheco.equalsIgnoreCase("Sim")) {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/form/div[8]/div/div/label/span")).click();
		}

		Print("Cadastro\\Passo 3 - Formulário preenchido 2");
		// Botão Enviar abre a tela do profissional
		driver.findElement(By.id("enviar")).click();
		
		// Validando se tela do Profissional carregou
		try {
			//usuário
			driver.findElement(By.id("user")).sendKeys(Keys.chord(login));
			//senha
			driver.findElement(By.id("pwd")).sendKeys(Keys.chord(senha));
			Print("Profissional\\Passo 1 - Acessa a tela Profissional");
			Thread.sleep(8000);
			
			driver.findElement(By.cssSelector("#form-signin > fieldset > div:nth-child(5) > button")).click();
	} catch (Exception ex) {
		Assert.fail("Tela de login do profissional não carregou");
	}
		//Confirmando a tela inicial do Profissional
		assertEquals("Duodoctor - Fale com um médico em minutos", driver.getTitle());
		driver.manage().window().maximize();
		//Clique no menu coronavirus
		driver.findElement(By.cssSelector("#side-menu > li:nth-child(2) > a")).click();
		//Validando a tela de pesquisa
		Print("Profissional\\Passo 2 - Acessa a tela com Menu Coronavirus");
		assertEquals("Triagens Coronavírus",
				driver.findElement(By.cssSelector("#page-wrapper > div > div > h2")).getText());
		//Clicar no botão Pesquisar
		driver.findElement(By.cssSelector("#page-wrapper > div > div > div.row > div > a:nth-child(1)")).click();
		driver.manage().window().setSize(new Dimension(1062, 639));
		// wait.until(ExpectedConditions.alertIsPresent());
		Thread.sleep(12000);
		//Validaando o pop-up de pesquisa
		Assert.assertEquals(
				"Filtrar Pacientes", driver
						.findElement(By.cssSelector(
								"div.modal-dialog.modal-lg > div.modal-content > div.modal-header > #myModalLabel"))
						.getText());
		// Campo para filtro de nome e e-mail
		driver.findElement(By.id("filtro_name")).sendKeys(Keys.chord(email));
		Print("Profissional\\Passo 3 - Pop-up de Pesquisa");
		//botão para realizar a pesquisa
		driver.findElement(By.cssSelector("#myModalFilter > form > div > div > div.modal-footer > button")).click();
		//VAlidando se o nome ou e-mail exibido soi o mesmo que o pesquisado cadstrado
		Assert.assertEquals(nome,
				driver.findElement(By.xpath("/html/body/div[1]/div/div/div/table/tbody/tr[1]/td[3]/strong")).getText());
		Print("Profissional\\Passo 4 - Pesquisa realizada com sucesso");
		//Validando o Laudo exibido
		Thread.sleep(5000);
		 driver.navigate (). refresh ();
		 Thread.sleep(5000);
		 driver.navigate (). refresh ();
		Thread.sleep(5000);
		 driver.navigate (). refresh ();
		 Thread.sleep(5000);
		 driver.navigate (). refresh ();
		 Thread.sleep(5000);
		 driver.navigate (). refresh ();
	try{
		if( driver.findElement(By.xpath("/html/body/div[1]/div/div/div/table/tbody/tr[1]/td[4]/a[1]")).isDisplayed()) 
		{
		Assert.assertEquals(laudo,
				driver.findElement(By.xpath("/html/body/div[1]/div/div/div/table/tbody/tr[1]/td[4]/a[1]")).getText());
		Print("Profissional\\Passo 5 - Laudo Exibido Com Sucesso");
			
		}
		}catch(Exception ex){
		Print("Profissional\\Passo 5 - Laudo Não Exibido");
		Assert.fail("Laudo não foi exibido");
		
		}
		//Validando se quando há febre o sistema exibe o item dipirona/paracetamol
		try {
			if (febre.equalsIgnoreCase("Sim")) {
				String sintomas = driver
						.findElement(By.xpath("/html/body/div[1]/div/div/div/table/tbody/tr[1]/td[4]/a[3]")).getText();
				Assert.assertEquals("Covid-19 Dipirona/Paracetamol", sintomas);

			}
		} catch (Exception ex) {
			Assert.fail("Remédio receitado sem paciente ter febre " + nome + "/" + laudo);
		}
	}
}