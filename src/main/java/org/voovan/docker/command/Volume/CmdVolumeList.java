package org.voovan.docker.command.Volume;

import org.voovan.docker.command.Cmd;
import org.voovan.docker.message.volume.VolumeInfo;
import org.voovan.docker.network.DockerClientException;
import org.voovan.docker.network.Result;
import org.voovan.tools.TObject;
import org.voovan.tools.json.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类文字命名
 *
 * @author helyho
 *         <p>
 *         JDocker Framework.
 *         WebSite: https://github.com/helyho/JDocker
 *         Licence: Apache v2 License
 */
public class CmdVolumeList extends Cmd{

    private Map<String,List<String>> filters;

    public CmdVolumeList() {
        filters = new HashMap<String,List<String>>();
    }


    public CmdVolumeList name(String ...name){
        filters.put("name", TObject.asList(name));
        return this;
    }

    public CmdVolumeList dangling(boolean dangling){
        filters.put("dangling", TObject.asList(dangling));
        return this;
    }

    public CmdVolumeList driver(String driver){
        filters.put("driver", TObject.asList(driver));
        return this;
    }

    public CmdVolumeList label(String key, String value){
        filters.put("label", TObject.asList(key+"="+value));
        return this;
    }

    public static CmdVolumeList newInstance(){
        return new CmdVolumeList();
    }

    @Override
    public List<VolumeInfo> send() throws Exception {
        addParameter("filters", JSON.toJSON(filters));

        Result result = getDockerHttpClient().run("GET","/volumes",getParameters());
        if(result!=null && result.getStatus()>=300){
            throw new DockerClientException(result.getMessage());
        }else{
            return VolumeInfo.load(result.getMessage());
        }

    }
}
