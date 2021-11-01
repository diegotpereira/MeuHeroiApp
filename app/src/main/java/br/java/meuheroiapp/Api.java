package br.java.meuheroiapp;

public class Api {

    private static final String ROOT_URL = "http://localhost:8000/v1/Api.php?apicall=";

    private static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    private static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    private static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    private static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";
}
