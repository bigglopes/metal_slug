package br.com.big.metalslug;

import br.com.big.metalslug.elementos.Jogo;

public class Main {
	
	public static void main ( String args[] )
	{
		Jogo p = new Jogo();
		p.setVisible( true );
		
		while( true )
		{
			p.repaint();
		}
	}

}
