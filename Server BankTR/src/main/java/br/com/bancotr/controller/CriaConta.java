package br.com.bancotr.controller;

import java.net.Socket;
import java.util.List;

import Entities.LoginEntity;

public interface CriaConta {
	
	public LoginEntity getByConta(String conta);
	public int create(Socket cliente,LoginEntity login);
	public List<LoginEntity> read();
	public void update(LoginEntity login);
	public void delete(LoginEntity login);

}
