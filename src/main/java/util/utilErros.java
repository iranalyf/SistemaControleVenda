package util;

public class utilErros {
	
	public static String getMensagemErro(Exception e){
		while(e.getCause() != null){
			e = (Exception) e.getCause();
		}
		String retorno =  e.getMessage();
		return retorno;
	}

}
