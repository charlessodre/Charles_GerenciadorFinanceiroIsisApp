package com.charlessodre.apps.gerenciadorfinanceiroisis;

import android.Manifest;
import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actBaseListas;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actCategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actRegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgResumo;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.EnviarSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.FragmentHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.LerHistoricoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.PermissionsUtil;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class actPrincipal extends actBaseListas
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //Objetos tela
    private FloatingActionButton fab, fab1, fab2, fab3, fab4;
    private LinearLayout fabLayout1, fabLayout2, fabLayout3, fabLayout4;
    private ImageButton btnEsquerda;
    private ImageButton btnDireita;
    private TextView txtNomeMesResumo;

    //Fragmentos
    private frgResumo fragmentoResumo;

    //Atributos
    private boolean isFABOpen = false;

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_principal);

        //Carregmento inicial
        this.inicializaObjetos();
        super.setAddMesCalendar(0);
        this.setNomeMes();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case PermissionsUtil.PERMISSION_ALL: {

                if (grantResults.length > 0) {

                    List<Integer> indexesOfPermissionsNeededToShow = new ArrayList<>();

                    for (int i = 0; i < permissions.length; ++i) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            indexesOfPermissionsNeededToShow.add(i);
                        }
                    }

                    int size = indexesOfPermissionsNeededToShow.size();
                    if (size != 0) {
                        int i = 0;
                        boolean isPermissionGranted = true;

                        while (i < size && isPermissionGranted) {
                            isPermissionGranted = grantResults[indexesOfPermissionsNeededToShow.get(i)]
                                    == PackageManager.PERMISSION_GRANTED;
                            i++;
                        }

                        if (!isPermissionGranted) {

                            DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    verificaPermissoes();
                                }
                            };

                            MessageBoxHelper.show(this, this.getString(R.string.title_permissao_obrigatoria), this.getString(R.string.msg_permissoes_obrigatorias), 0, okListener);

                        }
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.atualizaFragmentos();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnEsquerdaResumo:
                super.setAddMesCalendar(-1);
                this.setNomeMes();
                this.atualizaFragmentos();

                break;
            case R.id.btnDireitaResumo:
                super.setAddMesCalendar(1);
                this.setNomeMes();
                this.atualizaFragmentos();
                break;

        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categoria_despesa) {
            // Handle the camera action

            Intent it = new Intent(this, actCategoriaDespesa.class);
            startActivityForResult(it, 0);


        } else if (id == R.id.nav_categoria_receita) {


            Intent it = new Intent(this, actCategoriaReceita.class);
            startActivityForResult(it, 0);

        } else if (id == R.id.nav_conta) {
            Intent it = new Intent(this, actConta.class);
            startActivityForResult(it, 0);

        } else if (id == R.id.nav_receita) {
            Intent it = new Intent(this, actReceita.class);
            startActivityForResult(it, 0);

        } else if (id == R.id.nav_despesa) {
            Intent it = new Intent(this, actDespesa.class);
            startActivityForResult(it, 0);

        } else if (id == R.id.nav_share) {

            Intent it = new Intent(this, actTransferencia.class);
            startActivityForResult(it, 0);

        } else if (id == R.id.nav_import_sms) {

            Intent it = new Intent(this, actRegraImportacaoSMS.class);
            startActivityForResult(it, 0);

            //verificaPermissoes();
           // LerHistoricoSMS.getSMSDetails(this);
        } else if (id == R.id.nav_send) {

            //exemplo_lista_single();
            verificaPermissoes();
            EnviarSMS sms = new EnviarSMS();

            sms.Enviar("+5521988548894", "Teste envio de mensagem pelo android. Feito por Charles" + DateUtils.getCurrentDatetime().toString());
            sms.Enviar("+5521964339672", "Teste envio de mensagem pelo android. Feito por Charles" + DateUtils.getCurrentDatetime().toString());
            ToastHelper.showToastLong(this, "SMS Enviado com sucesso!");



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Métodos

    private void verificaPermissoes() {
        PermissionsUtil.askPermissions(this);
    }


    @Override
    protected void inicializaObjetos() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // super.setMenuHome(this.getString(R.string.title_principal));
        super.setColorStatusBar(R.color.corTelaPrincipal);

        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fabLayout3 = (LinearLayout) findViewById(R.id.fabLayout3);
        fabLayout4 = (LinearLayout) findViewById(R.id.fabLayout4);

        fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);

        this.btnDireita = (ImageButton) findViewById(R.id.btnDireitaResumo);
        this.btnEsquerda = (ImageButton) findViewById(R.id.btnEsquerdaResumo);

        this.btnDireita.setOnClickListener(this);
        this.btnEsquerda.setOnClickListener(this);

        this.txtNomeMesResumo = (TextView) this.findViewById(R.id.txtNomeMesResumo);


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

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent it = new Intent(getBaseContext(), actCadTransferencia.class);
                startActivityForResult(it, 0);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent it = new Intent(getBaseContext(), actCadDespesa.class);
                startActivityForResult(it, 0);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent it = new Intent(getBaseContext(), actCadReceita.class);
                startActivityForResult(it, 0);
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent it = new Intent(getBaseContext(), actCadConta.class);
                startActivityForResult(it, 0);
            }
        });

        //Verifica se o fragmento já foi adicionado.
        this.fragmentoResumo = (frgResumo) FragmentHelper.findFragmentByTag(getSupportFragmentManager(), frgResumo.NOME_FRAGMENTO);

        if (this.fragmentoResumo == null)
            this.adicionaFragResumo();

    }

    private void adicionaFragResumo() {


        this.fragmentoResumo = new frgResumo();

        Bundle argument = new Bundle();

        FragmentHelper.addFragment(getSupportFragmentManager(), this.fragmentoResumo, argument, frgResumo.NOME_FRAGMENTO, R.id.frag_container_1);

    }

    private void showFABMenu() {

        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);
        fabLayout4.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);

        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
        fabLayout4.animate().translationY(-getResources().getDimension(R.dimen.standard_195));

    }

    private void closeFABMenu() {

        isFABOpen = false;

        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0);
        fabLayout4.animate().translationY(0).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animator) {

                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                    fabLayout4.setVisibility(View.GONE);
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

    private void setNomeMes() {

        this.txtNomeMesResumo.setText(super.getNomeMesFormatado());
    }

    private void atualizaFragmentos() {
        this.fragmentoResumo.atualizaResumo(super.getAnoMes());

    }


    //TESTE
    private AlertDialog alerta;

    private void exemplo_lista_single() {
        //Lista de itens
        ArrayList<String> itens = new ArrayList<String>();
        itens.add("Ruim");
        itens.add("Mediano");
        itens.add("Bom");
        itens.add("Ótimo");

        //adapter utilizando um layout customizado (TextView)
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_alerta, itens);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Qualifique este software:");
        //define o diálogo como uma lista, passa o adapter.
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(actPrincipal.this, "posição selecionada=" + arg1, Toast.LENGTH_SHORT).show();
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }

}
