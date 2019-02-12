package hdfs.through.eclipse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

public class HdfsAccess {

	public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://test:8020");
        conf.set("hadoop.security.authentication", "kerberos");
        conf.addResource(new Path("/etc/hdfs-site.xml"));
        conf.addResource(new Path("/etc/core-site.xml"));
        System.setProperty("java.security.krb5.realm", "<Realm>");
        System.setProperty("java.security.krb5.kdc", "<KDC-server>");
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromKeytab("ragraw1"+"@"+
                            "<Realm>",
                    "/etc/.ragraw1.keytab");
            FileSystem fs = FileSystem.get(conf);
            FileStatus[] fsStatus = fs.listStatus(new Path("/user/hive/userdbs/ragraw1.db/test/"));
            System.out.println("Listing!!");
            for(int i = 0; i < fsStatus.length; i++){
                System.out.println(fsStatus[i].getPath().toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

}
