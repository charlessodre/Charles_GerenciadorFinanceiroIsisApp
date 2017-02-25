package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.Locale;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherPay implements TextWatcher {

    public static final String T = "TextWatcherPay";

    private final Locale locale;
    private final EditText editText;
    private String formatType;
    private String current = "";
    private boolean isDeleting;
    private String symbolCurrency;

    private boolean isNegative;

    protected int max_length = Integer.MAX_VALUE;


    /**
     * @param editText
     * @param locale Represents a specific geographical, political, or cultural region
     *@param formatType String formatting style like "%.2f $"
     */
    public TextWatcherPay(EditText editText, Locale locale, String formatType) {
        this.editText = new WeakReference<EditText>(editText).get();
        this.locale = locale != null ? locale : Locale.getDefault();
        this.symbolCurrency = NumberFormat.getCurrencyInstance(this.locale).getCurrency().getSymbol();
        this.formatType = formatType;

    }

    /**
     * @param editText
     * @param formatType String formatting style like "%.2f $"
     */
    public TextWatcherPay(EditText editText, String formatType)  {
        this.editText = new WeakReference<EditText>(editText).get();
        this.locale = Locale.getDefault();
        this.symbolCurrency = NumberFormat.getCurrencyInstance(this.locale).getCurrency().getSymbol();
        this.formatType = formatType;

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        if (after <= 0 && count > 0) {
            isDeleting = true;
        } else {
            isDeleting = false;
        }
        if (!s.toString().equals(current)) {
            editText.removeTextChangedListener(this);
            String clean_text = s.toString().replaceAll("[^\\d]", "");
            editText.setText(clean_text);
            editText.addTextChangedListener(this);
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //---- Log.i(T, "::onTextChanged:" + "CharSequence " + s +" start=" + start +" count=" + count +" before=" + before);
    }


    @Override
    public void afterTextChanged(Editable s) {
        // Log.i(T, "::afterTextChanged:" + "Editable "+s + "; Current "+current);

        if (!s.toString().equals(current)) {
            editText.removeTextChangedListener(this);

           String clean_text = s.toString().replaceAll("[^\\d]", "");

            if (isDeleting && s.length() > 0 && !Character.isDigit(s.charAt(s.length() - 1))) {
                clean_text = deleteLastChar(clean_text);
            }

//			Log.e(T, "::afterTextChanged:" + "clean_text "+clean_text);
            double v_value = 0;
            if (clean_text != null && clean_text.length() > 0) {
                v_value = Double.parseDouble(clean_text);


                String s_value = Double.toString(Math.abs(v_value / 100)); //TODO: Resolver problemas de numeros terminados em zero. Ex. 100,00
                int integerPlaces = s_value.indexOf('.');
                if (integerPlaces > max_length) {
                    v_value = Double.parseDouble(deleteLastChar(clean_text));
                }

            }

            if( (s.toString().startsWith("-")|| s.toString().endsWith("-") ))
                this.isNegative = true;
            else if (this.isNegative && v_value < 1 )
                    this.isNegative = false;


            if (this.isNegative)
                v_value = v_value * -1;

            String formatted_text = String.format(this.locale, formatType, v_value / 100); //TODO: Resolver problemas de numeros terminados em zero. Ex. 100,00

            formatted_text = this.symbolCurrency+" "+ formatted_text;

//			Log.e(T, "::afterTextChanged:" + "formatted_text "+formatted_text);
            current = formatted_text;
            editText.setText(formatted_text);
            editText.setSelection(formatted_text.length());
            editText.addTextChangedListener(this);
        }

    }


    private String deleteLastChar(String clean_text) {
        if (clean_text.length() > 0) {
            clean_text = clean_text.substring(0, clean_text.length() - 1);
        } else {
            clean_text = "0";
        }
        return clean_text;
    }


    public int getMax_length() {
        return max_length;
    }


    public void setMax_length(int max_length) {
        this.max_length = max_length;
    }


    public Double getValueWithoutMask() {
        return getValueWithoutMask(this.editText.getText().toString());
    }

    private Double getValueWithoutMask(String v_value) {

        double s_value = 0;
        double convert_value = 0;

        try {

            String clean_text = v_value.replaceAll("[^\\d]", "");

            if (clean_text != null && clean_text.length() > 0) {
                convert_value = Double.parseDouble(clean_text);

                s_value = (Math.abs(convert_value / 100)); //TODO: Resolver problemas de numeros terminados em zero. Ex. 100,00

                if (isNegative && s_value>0)
                    s_value = s_value * -1;

            }

        } catch (Exception e) {
            s_value = 0;
        }

        return s_value;


    }

}