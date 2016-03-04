package br.com.servicofacil.util;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * Created by P001241 on 24/02/2016.
 */
public class ValidadorDeCampos {

    public static boolean validateNotNull(View pView, String pMessage) {
        if (pView instanceof EditText) {
            EditText edText = (EditText) pView;
            Editable text = edText.getText();
            if (text != null) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    return true;
                }
            }
            // em qualquer outra condição é gerado um erro
            edText.setError(pMessage);
            edText.setFocusable(true);
            edText.requestFocus();
            return false;
        }
        return false;
    }


    public static boolean validateCPF(String CPF) {
        CPF = Mask.unmask(CPF);
        if (CPF.equals("00000000000") || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")) {
            return false;
        }
        char dig10, dig11;
        int sm, i, r, num, peso;
        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (CPF.charAt(i) - 48);
                //num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                // num = (int) (CPF.charAt(i) - 48);
                num = (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);
            return (dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));
        } catch (Exception erro) {
            return (false);
        }
    }

    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.equals(""));
    }


    public static boolean validateCNPJ(String cnpj) {
        if(cnpj == null || cnpj.length() != 14)
            return false;

        try {
            Long.parseLong(cnpj);
        } catch (NumberFormatException e) { // CNPJ não possui somente números
            return false;
        }

        int soma = 0;
        String cnpjCalc = cnpj.substring(0, 12);

        char chrCnpj[] = cnpj.toCharArray();
        for(int i = 0; i < 4; i++)
            if(chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9)
                soma += (chrCnpj[i] - 48) * (6 - (i + 1));

        for(int i = 0; i < 8; i++)
            if(chrCnpj[i + 4] - 48 >= 0 && chrCnpj[i + 4] - 48 <= 9)
                soma += (chrCnpj[i + 4] - 48) * (10 - (i + 1));

        int dig = 11 - soma % 11;
        cnpjCalc = (new StringBuilder(String.valueOf(cnpjCalc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
        soma = 0;
        for(int i = 0; i < 5; i++)
            if(chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9)
                soma += (chrCnpj[i] - 48) * (7 - (i + 1));

        for(int i = 0; i < 8; i++)
            if(chrCnpj[i + 5] - 48 >= 0 && chrCnpj[i + 5] - 48 <= 9)
                soma += (chrCnpj[i + 5] - 48) * (10 - (i + 1));

        dig = 11 - soma % 11;
        cnpjCalc = (new StringBuilder(String.valueOf(cnpjCalc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();

        return cnpj.equals(cnpjCalc);
    }


    public static boolean validarRenach(String CNH) {
        System.out.println("CNH : " + CNH);
        //CNH = desformatar(CNH);
        if (!CNH.matches("[0-9]{11}")) {
            return false;
        }

        if (CNH.equals("11111111111") || CNH.equals("22222222222") || CNH.equals("33333333333")
                || CNH.equals("44444444444") || CNH.equals("55555555555") || CNH.equals("66666666666")
                || CNH.equals("77777777777") || CNH.equals("88888888888") || CNH.equals("99999999999")
                || CNH.equals("00000000000")) {
            return false;
        }

        int[] fracao = new int[9];
        //String cnhCalc = CNH.substring(0, 11);
        int acumulador = 0;
        int inc = 2;
        for (int i = 0; i < 9; i++) {
            //fracao[i] = (Math.abs(Integer.parseInt(CNH.substring(i, 1)))) * inc;
            //System.out.println("Digito : " + CNH.substring(i, i+1));
            fracao[i] = Integer.parseInt(CNH.substring(i, i+1)) * inc;

            acumulador += fracao[i];
            inc++;
        }

        int resto = acumulador % 11;
        int digito1 = 0;
        if (resto > 1) {
            digito1 = 11 - resto;
        }
        acumulador = digito1 * 2;
        inc = 3;
        for (int i = 0; i < 9; i++) {
            //fracao[i] = (Math.abs(Integer.parseInt(CNH.substring(i, 1)))) * inc;
            fracao[i] = Integer.parseInt(CNH.substring(i, i+1)) * inc;
            acumulador += fracao[i];
            inc++;
        }

        resto = acumulador % 11;
        int digito2 = 0;
        if (resto > 1) {
            digito2 = 11 - resto;
        }
        if (digito1 == Integer.parseInt(CNH.substring(9, 10)) && digito2 == Integer.parseInt(CNH.substring(10, 11))) {
            return true;
        }

        return false;
    }
    private static String desformatar(String valor) {
        String str = "";
        String caracter = "";
        for (int i = 0; i < valor.length(); i++) {
            caracter = valor.substring(i, 1);
            if (ehNumero(caracter)) {
                str += caracter;
            }
        }
        return str;
    }

    private static boolean ehNumero(String caracter) {
        for (int i = 0; i <= 9; i++) {
            if (caracter.equals("" + i)) {
                return true;
            }
        }

        return false;
    }

}
