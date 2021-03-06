package org.voovan.docker.command.Volume;

import org.voovan.docker.command.Cmd;
import org.voovan.docker.network.DockerClientException;
import org.voovan.docker.network.Result;

/**
 * 类文字命名
 *
 * @author helyho
 *         <p>
 *         JDocker Framework.
 *         WebSite: https://github.com/helyho/JDocker
 *         Licence: Apache v2 License
 */
public class CmdVolumeRemove extends Cmd{

    private String nameOrId;

    public CmdVolumeRemove(String nameOrId) {
        this.nameOrId = nameOrId;
    }

    public static CmdVolumeRemove newInstance(String nameOrId){
        return new CmdVolumeRemove(nameOrId);
    }

    public CmdVolumeRemove force(boolean force){
        addParameter("force",force);
        return this;
    }


    @Override
    public String send() throws Exception {
        Result result = getDockerHttpClient().run("DELETE","/volumes/"+nameOrId,getParameters());
        if(result!=null && result.getStatus()>=300){
            throw new DockerClientException(result.getMessage());
        }else{
            return result.getMessage();
        }

    }
}
