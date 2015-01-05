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
public class FotosFragment extends Fragment {
    // All static variables
    static final String URL = "http://www.saballuts.org/jooSaballut/index.php?option=com_content&view=article&id=429&tmpl=component";
    WebView webview;
    View rootView,rootView2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView2 != null) {
            return rootView2;
        }
        rootView = inflater.inflate(R.layout.fotos, container, false);
        webview = (WebView) rootView.findViewById(R.id.webView);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:")) {
                    url = url + "&tmpl=component";
                    view.loadUrl(url);

                } else {
                    view.loadUrl(url);
                }
                return true;
            }

        });
        webview.setInitialScale(100);
        webview.setFocusable(true);
        webview.setFocusableInTouchMode(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setDisplayZoomControls(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);


        webview.loadUrl(URL);

        return rootView;
    }
    @Override
    public void onDestroyView() {
        rootView2 = rootView;
        super.onDestroyView();
    }


}



