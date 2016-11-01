package org.voovan.docker.test.command;

import junit.framework.TestCase;
import org.voovan.docker.Global;
import org.voovan.docker.command.Exec.CmdExecCreate;
import org.voovan.docker.command.Exec.CmdExecInfo;
import org.voovan.docker.command.Exec.CmdExecStart;
import org.voovan.docker.command.Network.CmdNetworkCreate;
import org.voovan.docker.command.Network.CmdNetworkList;
import org.voovan.docker.command.Network.CmdNetworkRemove;
import org.voovan.tools.TEnv;
import org.voovan.tools.json.JSON;
import org.voovan.tools.log.Logger;

/**
 * 类文字命名
 *
 * @author helyho
 *         <p>
 *         JDocker Framework.
 *         WebSite: https://github.com/helyho/JDocker
 *         Licence: Apache v2 License
 */
public class NetworkUtil extends TestCase {

    public void setUp() {
        Global.DOCKER_REST_HOST = "127.0.0.1";
        Global.DOCKER_REST_PORT = 2735;
        Global.DEBUG = true;
    }

    public String formatJSON(Object obj) {
        return JSON.formatJson(JSON.toJSON(obj));
    }

    public void testNetworkList() throws Exception {
        CmdNetworkList cmdNetworkList = CmdNetworkList.newInstance();
        Object data = cmdNetworkList.name("voovan").send();
        cmdNetworkList.close();
        Logger.info(formatJSON(data));
    }

    public void testNetworkCreate() throws Exception {
        CmdNetworkCreate cmdNetworkCreate = CmdNetworkCreate.newInstance();
        Object data = cmdNetworkCreate.name("voovan_p").driver("overlay").label("adfadf","dfadfadf").ipamConfig().send();
        cmdNetworkCreate.close();
        Logger.info(formatJSON(data));
    }

    public void testNetworkRemove() throws Exception {
        CmdNetworkRemove cmdNetworkRemove = CmdNetworkRemove.newInstance("50berova8jjttexezcfgofg17");
        Object data = cmdNetworkRemove.send();
        cmdNetworkRemove.close();
        Logger.info(formatJSON(data));
    }
}


