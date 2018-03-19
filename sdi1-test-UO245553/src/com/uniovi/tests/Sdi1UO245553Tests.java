package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_UserListView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Sdi1UO245553Tests {

	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox = "C:\\Users\\danyd\\Downloads\\Firefox46.0.win\\Firefox46.win\\FirefoxPortable.exe";

	// En MACOSX (Debe ser la versión 46.0 y desactivar las actualizaciones
	// automáticas):
	// static String PathFirefox =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	@Test
	public void RegVal() {
		PO_HomeView.clickOption(driver, "signup", "class", "signUp");

		// Introducimos datos válidos
		PO_RegisterView.fillForm(driver, "Pepe", "pepe@gmail.com", "123456", "123456");

		// Comprobamos que se hizo el registro correctamente y que nos redirecciona a la
		// página que lista todos los usuarios de la aplicación
		PO_View.checkElement(driver, "text",
				"Los usuarios que actualmente figuran " + "en el sistema son los siguientes:");
	}

	@Test
	public void RegInVal() {
		PO_HomeView.clickOption(driver, "signup", "class", "signUp");

		// Introducimos datos con repetición de contraseña inválidas
		PO_RegisterView.fillForm(driver, "Paco", "paco@gmail.com", "123456", "567890");

		// Comprobamos que se muestra el error de repetición de contraseña incorrecta
		PO_View.checkElement(driver, "text", "Las contraseñas no coinciden");
	}

	@Test
	public void InVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Introducimos datos válidos
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Comprobamos que se hizo el login correctamente y que nos redirecciona a la
		// página que lista todos los usuarios de la aplicación
		PO_View.checkElement(driver, "text",
				"Los usuarios que actualmente figuran " + "en el sistema son los siguientes:");
	}

	@Test
	public void InInVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Introducimos datos válidos
		PO_LoginView.fillForm(driver, "sara@gmail.com", "123456");

		// Comprobamos que se muestra el error en el login
		PO_View.checkElement(driver, "text", "El email o la contraseña son incorrectos");
	}

	@Test
	public void LisUsrVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Comprobamos que se hizo el login correctamente y que nos redirecciona a la
		// página que lista todos los usuarios de la aplicación
		PO_View.checkElement(driver, "text",
				"Los usuarios que actualmente figuran " + "en el sistema son los siguientes:");

		// Comprobamos que la opción usuarios/listar usuarios nos redirecciona a la
		// lista de usuarios
		PO_HomeView.goToUsersList(driver);

		// Comprobamos que estamos en dicha vista
		PO_View.checkElement(driver, "text",
				"Los usuarios que actualmente figuran " + "en el sistema son los siguientes:");

		// Contamos el número de usuarios
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
	}

	@Test
	public void LisUsrInVal() {
		// Sin tener un usuario en sesión introducimos la url para ir al listado de los
		// usuarios y nos redirecciona al login ya que se necesita estar logueado para
		// acceder a esta vista
		driver.navigate().to(URL + "/users/list");

		// Comprobamos que estamos en la pantalla de login
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	@Test
	public void BusUsrVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Primero iniciamos sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// A continuación rellenamos el campo de la búsqueda
		PO_UserListView.fillForm(driver, "car");

		// Contamos el número de usuarios
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
	}

	@Test
	public void BusUsrInVal() {
		// Sin tener un usuario en sesión introducimos la url para ir al listado de los
		// usuarios ya que no hay otra forma de acceder al buscador
		driver.navigate().to(URL + "/users/list");

		// Comprobamos que estamos en la pantalla de login
		PO_View.checkElement(driver, "text", "Identifícate");
	}

}
