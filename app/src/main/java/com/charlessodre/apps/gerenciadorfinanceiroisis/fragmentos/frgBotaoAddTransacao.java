package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadDespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadTransferencia;

/**
 * A simple {@link Fragment} subclass.
 */
public class frgBotaoAddTransacao extends Fragment {

    //Objetos tela
    private FloatingActionButton fab, fabTransferencia, fabDespesa, fabReceita, fabConta, fabCartaoCredito;
    private LinearLayout fabLayoutTranferencia, fabLayoutDespesa, fabLayoutReceita, fabLayoutConta,fabLayoutCartoCredito;

    //Atributos
    private View rootView;
    private boolean isFABOpen = false;


    //Constantes
    public static final String NOME_FRAGMENTO = "FRAG_BOTAO_ADD";

    public frgBotaoAddTransacao() {
        // Required empty public constructor
    }


    public static frgBotaoAddTransacao newInstance() {

        frgBotaoAddTransacao novoFragmento = new frgBotaoAddTransacao();

        return novoFragmento;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.frg_botao_add_transacao, container, false);

        this.inicializaObjetos();

        return this.rootView;
    }

    protected void inicializaObjetos() {


        fabLayoutTranferencia = (LinearLayout) this.rootView.findViewById(R.id.fabLayoutTranferencia);
        fabLayoutDespesa = (LinearLayout) this.rootView.findViewById(R.id.fabLayoutDespesa);
        fabLayoutReceita = (LinearLayout) this.rootView.findViewById(R.id.fabLayoutReceita);
        fabLayoutConta = (LinearLayout) this.rootView.findViewById(R.id.fabLayoutConta);
        fabLayoutCartoCredito = (LinearLayout)this.rootView.findViewById(R.id.fabLayoutCartoCredito);

        fab = (FloatingActionButton) this.rootView.findViewById(R.id.fabAdd);
        fabTransferencia = (FloatingActionButton) this.rootView.findViewById(R.id.fabTransferencia);
        fabDespesa = (FloatingActionButton) this.rootView.findViewById(R.id.fabDespesa);
        fabReceita = (FloatingActionButton) this.rootView.findViewById(R.id.fabReceita);
        fabConta = (FloatingActionButton) this.rootView.findViewById(R.id.fabConta);
        fabCartaoCredito = (FloatingActionButton) this.rootView.findViewById(R.id.fabCartaoCredito);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        fabTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent it = new Intent(getContext(), actCadTransferencia.class);
                startActivityForResult(it, 0);
            }
        });

        fabCartaoCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent it = new Intent(getContext(), actCadDespesaCartaoCredito.class);
                startActivityForResult(it, 0);
            }
        });

        fabDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent it = new Intent(getContext(), actCadDespesa.class);
                startActivityForResult(it, 0);
            }
        });

        fabReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent it = new Intent(getContext(), actCadReceita.class);
                startActivityForResult(it, 0);
            }
        });

        fabConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent it = new Intent(getContext(), actCadConta.class);
                startActivityForResult(it, 0);
            }
        });

    }

    private void showFABMenu() {

        isFABOpen = true;
        fabLayoutTranferencia.setVisibility(View.VISIBLE);
        fabLayoutDespesa.setVisibility(View.VISIBLE);
        fabLayoutReceita.setVisibility(View.VISIBLE);
        fabLayoutConta.setVisibility(View.VISIBLE);
        fabLayoutCartoCredito.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);

        fabLayoutTranferencia.animate().translationY(-getResources().getDimension(R.dimen.distance_fabTransferencia));
        fabLayoutCartoCredito.animate().translationY(-getResources().getDimension(R.dimen.distance_fabCartaoCredito));
        fabLayoutDespesa.animate().translationY(-getResources().getDimension(R.dimen.distance_fabDespesa));
        fabLayoutReceita.animate().translationY(-getResources().getDimension(R.dimen.distance_fabReceita));
        fabLayoutConta.animate().translationY(-getResources().getDimension(R.dimen.distance_fabConta));


    }

    private void closeFABMenu() {

        isFABOpen = false;

        fab.animate().rotationBy(-180);
        fabLayoutTranferencia.animate().translationY(0);
        fabLayoutCartoCredito.animate().translationY(0);
        fabLayoutDespesa.animate().translationY(0);
        fabLayoutReceita.animate().translationY(0);
        fabLayoutConta.animate().translationY(0).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animator) {

                if (!isFABOpen) {
                    fabLayoutTranferencia.setVisibility(View.GONE);
                    fabLayoutDespesa.setVisibility(View.GONE);
                    fabLayoutReceita.setVisibility(View.GONE);
                    fabLayoutConta.setVisibility(View.GONE);
                    fabLayoutCartoCredito.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }


            @Override
            public void onAnimationRepeat(Animator animator) {
            }


        });


    }

}
