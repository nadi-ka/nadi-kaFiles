import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public Main() throws URISyntaxException {
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();

        //getting of the amount and the list of requests;
        URIBuilder builder1 = new URIBuilder("https://api.vk.com/method/wall.get?");
        builder1.setParameter("access_token",
                "101dd4793d92bff86795fa86a22a26b2b9b0e10b1e77e64ea61f0a4b32dcb2ca032e3d5f261587d295e90")
                .setParameter("owner_id", "565014124")
                .setParameter("v", "5.101");
        HttpGet request1 = new HttpGet(builder1.build());
        HttpResponse response1 = client.execute(request1);
        System.out.println(EntityUtils.toString((response1.getEntity())));

        //posting of the message
        URIBuilder builder2 = new URIBuilder("https://api.vk.com/method/wall.post?");
        builder2.setParameter("access_token",
                "101dd4793d92bff86795fa86a22a26b2b9b0e10b1e77e64ea61f0a4b32dcb2ca032e3d5f261587d295e90")
                .setParameter("owner_id", "565014124")
                .setParameter("message", "test message334")
                .setParameter("v", "5.101");
        HttpGet request2 = new HttpGet(builder2.build());
        HttpResponse response2 = client.execute(request2);
        System.out.println(EntityUtils.toString(response2.getEntity()));

        //changing of the message
        URIBuilder builder3 = new URIBuilder("https://api.vk.com/method/wall.edit?");
        builder3.setParameter("access_token",
                "101dd4793d92bff86795fa86a22a26b2b9b0e10b1e77e64ea61f0a4b32dcb2ca032e3d5f261587d295e90")
                .setParameter("owner_id", "565014124")
                .setParameter("post_id", "2")
                .setParameter("message", "test message555")
                .setParameter("v", "5.101");
        HttpGet request3 = new HttpGet(builder3.build());
        HttpResponse response3 = client.execute(request3);
        System.out.println(EntityUtils.toString(response3.getEntity()));

        //deleting of the message
        URIBuilder builder4 = new URIBuilder("https://api.vk.com/method/wall.delete?");
        builder4.setParameter("access_token",
                "101dd4793d92bff86795fa86a22a26b2b9b0e10b1e77e64ea61f0a4b32dcb2ca032e3d5f261587d295e90")
                .setParameter("owner_id", "565014124")
                .setParameter("post_id", "2")
                .setParameter("v", "5.101");
        HttpGet request4 = new HttpGet(builder4.build());
        HttpResponse response4 = client.execute(request4);
        System.out.println(EntityUtils.toString(response4.getEntity()));

        //to make sure, that we deleted the message, let's use method wall.search;
        URIBuilder builder5 = new URIBuilder("https://api.vk.com/method/wall.search?");
        builder5.setParameter("access_token",
                "101dd4793d92bff86795fa86a22a26b2b9b0e10b1e77e64ea61f0a4b32dcb2ca032e3d5f261587d295e90")
                .setParameter("owner_id", "565014124")
                .setParameter("query", "test_message555")
                .setParameter("v", "5.101");
        HttpGet request5 = new HttpGet(builder5.build());
        HttpResponse response5 = client.execute(request5);
        System.out.println(EntityUtils.toString(response5.getEntity()));


    }
}
