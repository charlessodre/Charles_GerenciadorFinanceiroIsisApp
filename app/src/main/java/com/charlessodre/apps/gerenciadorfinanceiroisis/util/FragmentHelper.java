package com.charlessodre.apps.gerenciadorfinanceiroisis.util;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * Created by charl on 14/09/2016.
 */
public class FragmentHelper {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int containerView) {
        FragmentHelper.addFragment(fragmentManager, fragment, null, containerView);
    }

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, String tagFragment, int containerView) {
        FragmentHelper.addFragment(fragmentManager, fragment, null, tagFragment, containerView);
    }

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, Bundle arguments, String tagFragment, int containerView) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (arguments != null)
            fragment.setArguments(arguments);

        fragmentTransaction.add(containerView, fragment, tagFragment);

        fragmentTransaction.commit();
    }

    public static Fragment findFragmentByTag(FragmentManager fragmentManager, String tagFragment) {

        return fragmentManager.findFragmentByTag(tagFragment);

    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);

        fragmentTransaction.commit();
    }

    public static void removeFragmentWithStateLoss(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);

        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void detachFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.detach(fragment);

        fragmentTransaction.commit();
    }

    public static void atachFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.attach(fragment);

        fragmentTransaction.commit();
    }

    public static void detachFragmentWithStateLoss(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.detach(fragment);

        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void atachFragmentWithStateLoss(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.attach(fragment);

        fragmentTransaction.commitAllowingStateLoss();
    }


    public static void detachAndAtachFragment(FragmentManager fragmentManager, Fragment detachFragment,  Fragment atachFragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.detach(detachFragment);
        fragmentTransaction.attach(atachFragment);

        fragmentTransaction.commit();
    }


    public static void detachAndAtachFragmentWithStateLoss(FragmentManager fragmentManager, Fragment detachFragment,  Fragment atachFragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.detach(detachFragment);
        fragmentTransaction.attach(atachFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int containerView) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(containerView, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void replaceFragmentWithStateLoss(FragmentManager fragmentManager, Fragment fragment, int containerView) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(containerView, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public static String getTextEditTextFragment(FragmentManager fragmentManager, String tagFragment, int idEditText) {
        String text = "";

        Fragment fragment = fragmentManager.findFragmentByTag(tagFragment);

        if (fragment != null) {
            EditText editText = (EditText) fragment.getView().findViewById(idEditText);

            if (editText != null) {
                text = editText.getText().toString().trim();
            }

        }

        return text;

    }

    public static int getSpinnerSelectedItemPositionFragment(FragmentManager fragmentManager, String tagFragment, int idSpinner) {
        int position = 0;

        Fragment fragment = fragmentManager.findFragmentByTag(tagFragment);

        if (fragment != null) {
            Spinner spinner = (Spinner) fragment.getView().findViewById(idSpinner);

            if (spinner != null) {
                position = spinner.getSelectedItemPosition();
            }
        }

        return position;

    }


    public static int getSpinnerSelectedItemID(FragmentManager fragmentManager, String tagFragment, int idSpinner) {
        int idItemSelected = 0;

        Fragment fragment = fragmentManager.findFragmentByTag(tagFragment);

        if (fragment != null) {
            Spinner spinner = (Spinner) fragment.getView().findViewById(idSpinner);

            if (spinner != null) {
                idItemSelected = Integer.valueOf(spinner.getSelectedItem().toString());
            }
        }

        return idItemSelected;

    }

}
