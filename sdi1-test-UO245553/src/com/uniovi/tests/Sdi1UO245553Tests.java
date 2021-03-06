package com.uniovi.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_CreatePublicationView;
import com.uniovi.tests.pageobjects.PO_FriendRequestView;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_UserListView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Sdi1UO245553Tests {

	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox = "C:\\Users\\danyd\\Documents\\Firefox46.win\\FirefoxPortable.exe";

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

	@Test
	public void InvVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// A continuación rellenamos el campo de la búsqueda para encontrar a un usuario
		PO_UserListView.fillForm(driver, "carlos");

		// Clicamos en el botón de Añadir Amigo
		PO_UserListView.sendPetition(driver);
	}

	@Test
	public void InvInVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// A continuación rellenamos el campo de la búsqueda para encontrar a un usuario
		PO_UserListView.fillForm(driver, "Marta");

		// Clicamos en el botón de Añadir Amigo
		PO_UserListView.sendPetition(driver);

		// Comprobamos que el texto del botón cambia y además está deshabilitado
		PO_View.checkElement(driver, "text", "Enviada");
		WebElement boton = driver.findElement(By.id("btnRequest"));
		assertFalse(boton.isEnabled());
	}

	@Test
	public void LisInvVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Enviamos una petición de amistad a un usuario
		PO_UserListView.fillForm(driver, "Laura");
		// Clicamos en el botón de Añadir Amigo
		PO_UserListView.sendPetition(driver);

		PO_View.checkElement(driver, "text", "Enviada");

		// Cerramos sesion con el usuario actual
		PO_HomeView.clickOption(driver, "logout");

		// Iniciamos sesion con el usuario al que le enviamos la petición
		PO_LoginView.fillForm(driver, "laura@gmail.com", "123456");

		// Clicamos en la opción de listar solicitudes de amistad
		PO_HomeView.clickOption(driver, "friendRequests", "class", "solicitudesAmistad");

		// Comprobamos que hay una solicitud y es del usuario correcto
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);

		PO_View.checkElement(driver, "text", "Daniel");
	}

	@Test
	public void AcepInvVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Enviamos una petición de amistad a un usuario
		PO_UserListView.fillForm(driver, "Pelayo");
		// Clicamos en el botón de Añadir Amigo
		PO_UserListView.sendPetition(driver);

		PO_View.checkElement(driver, "text", "Enviada");

		// Cerramos sesion con el usuario actual
		PO_HomeView.clickOption(driver, "logout");

		// Iniciamos sesion con el usuario al que le enviamos la petición
		PO_LoginView.fillForm(driver, "pelayo@gmail.com", "123456");

		// Clicamos en la opción de listar solicitudes de amistad
		PO_HomeView.clickOption(driver, "friendRequests", "class", "solicitudesAmistad");

		// Clicamos en el botón aceptar
		PO_FriendRequestView.clickBotonAceptar(driver);

		// Comprobamos que ya no hay más solicitudes
		PO_View.checkElement(driver, "text", "No tienes ninguna petición de amistad pendiente");

		// Volvemos a la lista de usuarios y comprobamos que el usuario 1 y el 2 son
		// amigos
		PO_HomeView.goToUsersList(driver);

		PO_UserListView.fillForm(driver, "Daniel");
		PO_View.checkElement(driver, "text", "Amigo");

		// Cerramos sesión y comprobamos que el usuario 1 tambien es amigo del usuario 2
		PO_HomeView.clickOption(driver, "logout");

		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		PO_UserListView.fillForm(driver, "Pelayo");

		PO_View.checkElement(driver, "text", "Amigo");
	}

	@Test
	public void ListAmiVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Enviamos una petición de amistad a un usuario
		PO_UserListView.fillForm(driver, "Raul");
		// Clicamos en el botón de Añadir Amigo
		PO_UserListView.sendPetition(driver);

		PO_View.checkElement(driver, "text", "Enviada");

		// Cerramos sesion con el usuario actual
		PO_HomeView.clickOption(driver, "logout");

		// Iniciamos sesion con el usuario al que le enviamos la petición
		PO_LoginView.fillForm(driver, "raul@gmail.com", "123456");

		// Clicamos en la opción de listar solicitudes de amistad
		PO_HomeView.clickOption(driver, "friendRequests", "class", "solicitudesAmistad");

		// Clicamos en el botón aceptar
		PO_FriendRequestView.clickBotonAceptar(driver);

		// Clicamos en la opción de listar usuarios
		PO_HomeView.clickOption(driver, "friendsList", "class", "listarAmigos");

		// Comprobamos que hay un usuario
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);

		PO_View.checkElement(driver, "text", "Daniel");

		// Cerramos sesion y comprobamos que el primer usuario también amigo del segundo

		PO_HomeView.clickOption(driver, "logout");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Clicamos en la opción de listar usuarios
		PO_HomeView.clickOption(driver, "friendsList", "class", "listarAmigos");

		// Comprobamos que hay un usuario
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 4);

		PO_View.checkElement(driver, "text", "Raul");
	}

	@Test
	public void PubVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Cargamos la vista de crear publicación
		PO_NavView.goToCreatePublication(driver);

		// Introducimos datos
		PO_CreatePublicationView.fillForm(driver, "Titulo1", "publicación1");

		// Comprobamos que se creo la publicación
		PO_View.checkElement(driver, "text", "Titulo1");
	}

	@Test
	public void LisPubVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Cargamos la vista de crear publicación
		PO_NavView.goToCreatePublication(driver);

		// Introducimos datos
		PO_CreatePublicationView.fillForm(driver, "Titulo1", "publicación1");

		// Comprobamos que se creo la publicación
		PO_View.checkElement(driver, "text", "Titulo1");

		// Vamos a otra vista
		PO_NavView.goToUsersList(driver);

		// Obtenemos las publicaciones del usuario en sesión
		PO_NavView.goToMyPublication(driver);

		// Comprobamos que se creo la publicación
		PO_View.checkElement(driver, "text", "Titulo1");
	}

	@Test
	public void LisPubAmiVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Enviamos una petición de amistad a un usuario
		PO_UserListView.fillForm(driver, "Carla");
		// Clicamos en el botón de Añadir Amigo
		PO_UserListView.sendPetition(driver);

		// Cargamos la vista de crear publicación
		PO_NavView.goToCreatePublication(driver);

		// Introducimos datos
		PO_CreatePublicationView.fillForm(driver, "Titulo1", "publicación3");

		// Cerramos sesion con el usuario actual
		PO_HomeView.clickOption(driver, "logout");

		// Iniciamos sesion con el usuario al que le enviamos la petición
		PO_LoginView.fillForm(driver, "carla@gmail.com", "123456");

		// Clicamos en la opción de listar solicitudes de amistad
		PO_HomeView.clickOption(driver, "friendRequests", "class", "solicitudesAmistad");

		// Clicamos en el botón aceptar
		PO_FriendRequestView.clickBotonAceptar(driver);

		// Listamos las publicaciones del amigo
		PO_HomeView.clickOption(driver, "friendsList", "class", "listarAmigos");

		driver.navigate().to(URL + "/publications/friendPublications/1");

		PO_View.checkElement(driver, "text", "Titulo1");

	}

	@Test
	public void LisPubAmiInVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Enviamos una petición de amistad a un usuario
		PO_UserListView.fillForm(driver, "Miguel");
		// Clicamos en el botón de Añadir Amigo
		PO_UserListView.sendPetition(driver);

		// Cargamos la vista de crear publicación
		PO_NavView.goToCreatePublication(driver);

		// Introducimos datos
		PO_CreatePublicationView.fillForm(driver, "Titulo1", "publicación3");

		// Cerramos sesion con el usuario actual
		PO_HomeView.clickOption(driver, "logout");

		// Iniciamos sesion con el usuario al que le enviamos la petición
		PO_LoginView.fillForm(driver, "miguel@gmail.com", "123456");

		// Clicamos en la opción de listar solicitudes de amistad
		PO_HomeView.clickOption(driver, "friendRequests", "class", "solicitudesAmistad");

		// Clicamos en el botón aceptar
		PO_FriendRequestView.clickBotonAceptar(driver);

		// Listamos las publicaciones del amigo
		PO_HomeView.clickOption(driver, "friendsList", "class", "listarAmigos");

		driver.navigate().to(URL + "/publications/friendPublications/3");

		PO_View.checkElement(driver, "id", "indexImg");

	}
	
	

	@Test
	public void AdInVal() {
		driver.navigate().to(URL + "/admin/login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "admin@gmail.com", "123456");

		// Comprobamos que se cargan todos los usuarios de la aplicación
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 9);
	}

	@Test
	public void AdInInVal() {
		driver.navigate().to(URL + "/admin/login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Comprobamos que se muestra el error
		PO_View.checkElement(driver, "text", "El usuario no tiene rol de admin");
	}

	@Test
	public void AdLisUsrVal() {
		driver.navigate().to(URL + "/admin/login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "admin@gmail.com", "123456");

		// Comprobamos que se cargan todos los usuarios de la aplicación
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 9);
	}

	@Test
	public void AdBorUsrVal() {
		driver.navigate().to(URL + "/admin/login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "admin@gmail.com", "123456");

		// Borramos el último usuario
		driver.navigate().to(URL + "/admin/delete/10");

		// Comprobamos que ya no está el usuario en la aplicación
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Lara", 2);
	}

	@Test
	public void AdBorUsrInVal() {
		PO_HomeView.clickOption(driver, "login", "class", "login");

		// Cargamos un usuario en sesión
		PO_LoginView.fillForm(driver, "daniel@gmail.com", "123456");

		// Borramos el último usuario
		driver.navigate().to(URL + "/admin/delete/10");

		// Comprobamos que se carga la página principal de la aplicación
		PO_View.checkElement(driver, "id", "indexImg");
	}
}
