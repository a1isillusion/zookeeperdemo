package nullguo.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {   ZkClient zkClient = new ZkClient("111.230.100.33:2181",5000);
        System.out.println( "Hello World!" );
        zkClient.addAuthInfo("digest", "foo:true".getBytes());
        String path="/zookeeper";
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
			
			public void handleChildChange(String parentpath, List<String> currentchilds) throws Exception {
				System.out.println(parentpath+"!!"+currentchilds);
			}
		});
        zkClient.subscribeDataChanges(path+"/a", new IZkDataListener() {
			
			public void handleDataDeleted(String path) throws Exception {
				System.out.println("delete!!"+path);
			}
			
			public void handleDataChange(String path, Object data) throws Exception {
				System.out.println(path+"!!change!!"+data);
			}
		});
        zkClient.createEphemeral(path+"/a","adata!",Ids.CREATOR_ALL_ACL);
        Thread.sleep(1000);
        zkClient.createEphemeral(path+"/b");
        Thread.sleep(1000);
        System.out.println(zkClient.getChildren(path));
        Thread.sleep(1000);
        ZkClient zkClient2=new ZkClient("111.230.100.33:2181",5000);
        zkClient2.writeData(path+"/a","zk2aa!!");
    }
}
