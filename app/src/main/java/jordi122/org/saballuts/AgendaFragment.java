package jordi122.org.saballuts;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Jordi.Martinez on 04/08/2014.
 */
public class AgendaFragment extends Fragment {
    // All static variables
    static final String URL = "http://colla.saballuts.org/index.php?option=com_jcalpro&Itemid=45&extmode=cal&print=1&tmpl=component";
    View rootView,rootView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView2 != null) {
            return rootView2;
        }
        rootView = inflater.inflate(R.layout.agenda, container, false);
        WebView webview = (WebView) rootView.findViewById(R.id.webView);
        WebSettings webSettings = webview.getSettings();
        // Habilita JavaScript
        webSettings.setJavaScriptEnabled(true);
        // Els nous clients http seran també interns
        webview.setWebViewClient(new WebViewClient());
        // Fixació del tamany de mida mínim
        webview.getSettings().setMinimumFontSize(16);
        // Zoom a pantalla
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.setInitialScale(100);

        webview.setFocusable(true);
        webview.setFocusableInTouchMode(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setDisplayZoomControls(true);
        webview.getSettings().setBuiltInZoomControls(true);

        webview.loadUrl(URL);
        return rootView;
    }
    @Override
    public void onDestroyView() {
        rootView2 = rootView;
        super.onDestroyView();
    }

}
