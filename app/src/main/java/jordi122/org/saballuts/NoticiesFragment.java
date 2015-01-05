package jordi122.org.saballuts;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Created by Jordi.Martinez on 04/08/2014.
 */
public class NoticiesFragment extends Fragment {
    ListAdapter adapter,adapter2;
    NodeList nl;
    XMLParser parser;
    View rootView;

    // All static variables
    static final String[] URL = {"http://www.saballuts.org/jooSaballut/index.php?option=com_content&view=category&id=9&format=feed&type=rss","http://www.saballuts.org/jooSaballut/index.php?option=com_content&view=category&id=19&format=feed&type=rss","http://www.saballuts.org/jooSaballut/index.php?option=com_content&view=category&id=20&format=feed&type=rss"};
    // XML node keys
    static final String KEY_ITEM = "item"; // parent node
    // static final String KEY_link = "link";
    static final String KEY_NAME = "title";
    static final String KEY_LINK = "link";
    static final String KEY_DESC = "description";
    static final String KEY_DATE = "pubdate";
    ListView lvNoticies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parser =  new XMLParser();
        new MyAsync().execute();
        // Retain this fragment across configuration changes.
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.noticies, container, false);
        lvNoticies = (ListView) rootView.findViewById(R.id.listNoticies);
        lvNoticies.setAdapter(adapter2);
        return rootView;
    }

    public void tractarLlista(ArrayList<HashMap<String, String>> menuItems) {

        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();

            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            //map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
            map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
            map.put(KEY_LINK, parser.getValue(e, KEY_LINK));
            map.put(KEY_DATE, parser.getValue(e, KEY_DATE));
            // adding HashList to ArrayList
            menuItems.add(map);
        }


    }
    public void tractarAdaptador(ArrayList<HashMap<String, String>> menuItems) {


        // Adding menuItems to ListView
        Collections.sort(menuItems, new MapComparator(KEY_DATE));
        adapter = new SimpleAdapter(getActivity(), menuItems,
                R.layout.list_item,
                new String[] { KEY_NAME, KEY_DESC, KEY_LINK }, new int[] {
                R.id.titol, R.id.descripcio, R.id.link });


        // selecting single ListView item
        //ListView lv = getListView();

        lvNoticies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //   @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String link = ((TextView) view.findViewById(R.id.link)).getText().toString();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link+"&tmpl=component"));
                startActivity(browserIntent);
            }
        });
    }


    private class MyAsync extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {
            ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

            for (int i = 0;i< URL.length;i++) {
                String xml = parser.getXmlFromUrl(URL[i]); // getting XML
                String pattern = "(<!)(.+?)(-->)";
                xml = xml.replaceAll(pattern, "");
                pattern = "(<p\\s)(.+?)(>)";
                xml = xml.replaceAll(pattern, "");
                pattern = "(<p>)";
                xml = xml.replaceAll(pattern, "");
                pattern = "</p>";
                xml = xml.replaceAll(pattern, "");
                pattern = "(<span)(.+?)(>)";
                xml = xml.replaceAll(pattern, "");
                pattern = "</span>";
                xml = xml.replaceAll(pattern, "");
                pattern = "]]>";
                xml = xml.replaceAll(pattern, "");
                pattern = "(<img\\s)(.+?)(>)";
                xml = xml.replaceAll(pattern, "");
                Document doc = parser.getDomElement(xml); // getting DOM element
                nl = doc.getElementsByTagName(KEY_ITEM);
                tractarLlista(menuItems);
                System.out.println(nl.item(0).toString()+" polles");
            }
            tractarAdaptador(menuItems);
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void result) {
            lvNoticies.setAdapter(adapter);
            super.onPostExecute(result);

        }
    }
    @Override
    public void onDestroyView() {
        adapter2 = adapter;
        super.onDestroyView();
    }

}
