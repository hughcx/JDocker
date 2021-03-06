package org.voovan.docker.test.command;

import junit.framework.TestCase;
import org.voovan.docker.DockerGlobal;
import org.voovan.docker.command.Container.*;
import org.voovan.tools.TEnv;
import org.voovan.tools.TFile;
import org.voovan.tools.TString;
import org.voovan.tools.json.JSON;
import org.voovan.tools.log.Logger;

import java.io.File;
import java.nio.ByteBuffer;

/**
 * 类文字命名
 *
 * @author helyho
 *         <p>
 *         JDocker Framework.
 *         WebSite: https://github.com/helyho/JDocker
 *         Licence: Apache v2 License
 */
public class ContainersUnit extends TestCase {

    public ContainersUnit(String name) {
        super(name);
    }

    public String formatJSON(Object obj){
        return JSON.formatJson(JSON.toJSON(obj));
    }

    public void setUp(){
        DockerGlobal.DOCKER_REST_HOST = "127.0.0.1";
        DockerGlobal.DOCKER_REST_PORT = 2735;
        DockerGlobal.DEBUG = true;
    }

    public void testCreate() throws Exception {
        CmdContainerCreate createCmd =  CmdContainerCreate.newInstance("Voovan");
        createCmd.connect();
        Object data = createCmd.image("alpine").network("bridge")
                .bind("v_tx:/v_tx").link("dockerfly:dockerfly")
                .cmd("ping","127.0.0.1").env("author=helyho")
                .exposePort("tcp",22,"2222").send();
        createCmd.close();
        Logger.info(formatJSON(data));
    }

    public void testInfo() throws Exception {
        CmdContainerList cmdContainersList = CmdContainerList.newInstance();
        cmdContainersList.connect();
        Object data = cmdContainersList.name("dockerfly").all(true).send();
        cmdContainersList.close();
        Logger.info(formatJSON(data));
        while(true){
            cmdContainersList.connect();
            data = cmdContainersList.name("dockerfly").all(true).send();
            cmdContainersList.close();
            Logger.info(formatJSON(data));
            TEnv.sleep(1);
        }
    }

    public void testDetail() throws Exception {
        CmdContainerDetail cmdContainerDetail = CmdContainerDetail.newInstance("dockerfly");
        cmdContainerDetail.connect();
        Object data = cmdContainerDetail.send();
        cmdContainerDetail.close();
        Logger.info(formatJSON(data));
    }

    public void testRename() throws Exception {
        CmdContainerRename cmdContainerRename = CmdContainerRename.newInstance("53b3bd0cfd787f471ec9fbbd4b62d95d043df1249c818a77df7603cbb536fc60");
        Object data = cmdContainerRename.newName("dockfly_1").send();
        cmdContainerRename.close();
        Logger.info(formatJSON(data));
    }

    public void testUpdate() throws Exception {
        CmdContainerUpdate cmdContainerUpdate =  CmdContainerUpdate.newInstance("Voovan");
        cmdContainerUpdate.connect();
        Object data = cmdContainerUpdate.cpuPeriod(1000000).memory(250).send();
        cmdContainerUpdate.close();
        Logger.info(formatJSON(data));
    }

    public void testRemove() throws Exception {
        CmdContainerRemove cmdContainerRemove = CmdContainerRemove.newInstance("Voovan");
        cmdContainerRemove.connect();
        Object data = cmdContainerRemove.send();
        cmdContainerRemove.close();
        Logger.info(formatJSON(data));
    }

    public void testStart() throws Exception {
        CmdContainerStart cmdContainerStart = CmdContainerStart.newInstance("Voovan");
        cmdContainerStart.connect();
        Object data = cmdContainerStart.send();
        cmdContainerStart.close();
        Logger.info(formatJSON(data));
    }

    public void testStop() throws Exception {
        CmdContainerStop cmdContainerStop = CmdContainerStop.newInstance("Voovan");
        cmdContainerStop.connect();
        Object data = cmdContainerStop.send();
        cmdContainerStop.close();
        Logger.info(formatJSON(data));
    }

    public void testChange() throws Exception {
        CmdContainerChange cmdContainerChange = CmdContainerChange.newInstance("Voovan");
        cmdContainerChange.connect();
        Object data = cmdContainerChange.send();
        cmdContainerChange.close();
        Logger.info(formatJSON(data));
    }

    public void testTop() throws Exception {
        CmdContainerTop cmdContainerTop = CmdContainerTop.newInstance("Voovan");
        cmdContainerTop.connect();
        Object data = cmdContainerTop.send();
        cmdContainerTop.close();
        Logger.info(formatJSON(data));
    }

    public void testStats() throws Exception {
        CmdContainerStats cmdContainerStats = CmdContainerStats.newInstance("dockerfly");
        cmdContainerStats.connect();
        Object data = cmdContainerStats.send();
        cmdContainerStats.close();
        Logger.info(formatJSON(data));
    }

    public void testPause() throws Exception {
        CmdContainerPause cmdContainerPause = CmdContainerPause.newInstance("Voovan");
        cmdContainerPause.connect();
        Object data = cmdContainerPause.send();
        cmdContainerPause.close();
        Logger.info(formatJSON(data));
    }

    public void testUnPause() throws Exception {
        CmdContainerUnPause cmdContainerUnPause = CmdContainerUnPause.newInstance("Voovan");
        cmdContainerUnPause.connect();
        Object data = cmdContainerUnPause.send();
        cmdContainerUnPause.close();
        Logger.info(formatJSON(data));
    }

    public void testReStart() throws Exception {
        CmdContainerReStart cmdContainerReStart = CmdContainerReStart.newInstance("Voovan");
        cmdContainerReStart.connect();
        Object data = cmdContainerReStart.send();
        cmdContainerReStart.close();
        Logger.info(formatJSON(data));
    }

    public void testResize() throws Exception {
        CmdContainerResize cmdContainerResize = CmdContainerResize.newInstance("Voovan");
        cmdContainerResize.connect();
        Object data = cmdContainerResize.height(30).width(20).send();
        cmdContainerResize.close();
        Logger.info(formatJSON(data));
    }

    public void testAttach() throws Exception {
        CmdContainerAttach cmdContainerAttach = CmdContainerAttach.newInstance("voovan");
        cmdContainerAttach.connect();
        Object data = cmdContainerAttach.logs(true).stream(true).stdin(true)
                .stdout(true).stderr(true).send();
        cmdContainerAttach.close();
        Logger.info(formatJSON(data));
    }

    public void testAttachStream() throws Exception {
        CmdContainerAttach cmdContainerAttach = CmdContainerAttach.newInstance("voovan");
        cmdContainerAttach.stream(true);
        cmdContainerAttach.stdout(true);
        cmdContainerAttach.connect();
        cmdContainerAttach.send();

        int count = 0;
        while(true){
            String data = cmdContainerAttach.loadStream();
            if(data!=null) {
                Logger.simple(data);
            }
            TEnv.sleep(1000);
            count ++;

            if(count > 10){
                break;
            }
        }

        cmdContainerAttach.close();
    }

    public void testLogs() throws Exception {
        CmdContainerLogs cmdContainerLogs = CmdContainerLogs.newInstance("dockerfly");
        cmdContainerLogs.connect();
        Object data = cmdContainerLogs.send();
        cmdContainerLogs.close();
        Logger.info(TString.unConvertEscapeChar((String)data));
    }

    public void testCommit() throws Exception {
        CmdContainerCommit cmdContainerCommit = CmdContainerCommit.newInstance("Voovan");
        cmdContainerCommit.connect();
        Object data = cmdContainerCommit.repo("helyhotest").tag("latest").pause(true).send();
        cmdContainerCommit.close();
        Logger.info(formatJSON(data));
    }

    public void testKill() throws Exception {
        CmdContainerKill cmdContainerKill = CmdContainerKill.newInstance("Voovan");
        cmdContainerKill.connect();
        String data = cmdContainerKill.send();
        cmdContainerKill.close();
        Logger.info(formatJSON(data));
    }

    public void testExport() throws Exception {
        CmdContainerExport cmdContainerExport = CmdContainerExport.newInstance("Voovan");
        cmdContainerExport.connect();
        ByteBuffer data = cmdContainerExport.send();
        cmdContainerExport.close();
        TFile.writeFile(new File("/Users/helyho/x.tar"),data.array());
    }

    public void testArchiveGet() throws Exception {
        CmdContainerArchiveGet cmdContainerArchiveGet = CmdContainerArchiveGet.newInstance("Voovan");
        cmdContainerArchiveGet.connect();
        ByteBuffer data = cmdContainerArchiveGet.path("/etc/hosts").send();
        cmdContainerArchiveGet.close();
        TFile.writeFile(new File("/Users/helyho/x.tar"),data.array());
    }

    public void testArchivePut() throws Exception {
        CmdContainerArchivePut cmdContainerArchivePut = CmdContainerArchivePut.newInstance("Voovan","/Users/helyho/x.tar");
        cmdContainerArchivePut.connect();
        ByteBuffer data = cmdContainerArchivePut.path("/root").send();
        cmdContainerArchivePut.close();
        Logger.info(formatJSON(data));
    }
}

