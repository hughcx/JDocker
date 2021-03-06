package org.voovan.docker.command.Service;

import org.voovan.docker.command.Cmd;
import org.voovan.docker.message.service.ServiceInfo;
import org.voovan.docker.message.service.ServiceSpec;
import org.voovan.docker.message.service.atom.Mount;
import org.voovan.docker.message.service.atom.Network;
import org.voovan.docker.message.service.atom.Port;
import org.voovan.docker.message.service.atom.mode.Replicated;
import org.voovan.docker.network.DockerClientException;
import org.voovan.docker.network.Result;
import org.voovan.tools.TObject;

/**
 * 类文字命名
 *
 * @author helyho
 *         <p>
 *         JDocker Framework.
 *         WebSite: https://github.com/helyho/JDocker
 *         Licence: Apache v2 License
 */
public class CmdServiceUpdate extends Cmd {

    private ServiceSpec serviceSpec;
    private String nameOrId ;

    public CmdServiceUpdate(String nameOrId, Integer version) {
        this.nameOrId = nameOrId;
        addParameter("version",version);
        serviceSpec = new ServiceSpec();
    }

    public CmdServiceUpdate(ServiceInfo serviceInfo) {
        this.serviceSpec = serviceInfo.getSpec();
        this.nameOrId = serviceInfo.getId();
        addParameter("version",serviceInfo.getVersion().getIndex());
    }

    public CmdServiceUpdate name(String name){
        serviceSpec.setName(name);
        return this;
    }

    public CmdServiceUpdate image(String image){
        serviceSpec.getTaskTemplate().getContainer().setImage(image);
        return this;
    }

    public CmdServiceUpdate cmd(String ... cmd){
        serviceSpec.getTaskTemplate().getContainer().getArgs().addAll(TObject.asList(cmd));
        return this;
    }

    public CmdServiceUpdate env(String ... env){
        serviceSpec.getTaskTemplate().getContainer().getEnv().addAll(TObject.asList(env));
        return this;
    }

    public CmdServiceUpdate stopGracePeriod(long stopGracePeriod){
        serviceSpec.getTaskTemplate().getContainer().setStopGracePeriod(stopGracePeriod);
        return this;
    }

    public CmdServiceUpdate cpuLimit(float nanoCpu){
        serviceSpec.getTaskTemplate().getResource().getLimits().setNanoCPUs((long)(nanoCpu*1000000000L));
        return this;
    }

    public CmdServiceUpdate memoryLimit(long memoryByte){
        serviceSpec.getTaskTemplate().getResource().getLimits().setMemoryBytes(memoryByte*1024*1024);
        return this;
    }

    public CmdServiceUpdate cpuReservation(float nanoCpu){
        serviceSpec.getTaskTemplate().getResource().getReservations().setNanoCPUs((long)(nanoCpu*1000000000L));
        return this;
    }

    public CmdServiceUpdate memoryReservation(long memoryByte){
        serviceSpec.getTaskTemplate().getResource().getReservations().setMemoryBytes(memoryByte);
        return this;
    }


    public CmdServiceUpdate network(String network){
        serviceSpec.getNetworks().add(new Network(network));
        return this;
    }

    public CmdServiceUpdate mode(String mode){
        serviceSpec.getEndpoint().setMode(mode);
        return this;
    }


    public CmdServiceUpdate port(String protocol, int targetPort, int publishPort){
        serviceSpec.getEndpoint().getPorts().add(new Port(protocol,targetPort,publishPort));
        return this;
    }

    public CmdServiceUpdate portTcp(int targetPort, int publishPort){
        serviceSpec.getEndpoint().getPorts().add(new Port("tcp",targetPort,publishPort));
        return this;
    }

    public CmdServiceUpdate portUdp(int targetPort, int publishPort){
        serviceSpec.getEndpoint().getPorts().add(new Port("udp",targetPort,publishPort));
        return this;
    }


    public CmdServiceUpdate mountRead(String source, String target){
        serviceSpec.getTaskTemplate().getContainer().getMounts().add(new Mount(source,target,true));
        return this;
    }


    public CmdServiceUpdate mountReadWrite(String source, String target){
        serviceSpec.getTaskTemplate().getContainer().getMounts().add(new Mount(source,target,false));
        return this;
    }

    public CmdServiceUpdate mount(String type, String source, String target, boolean readOnly){
        serviceSpec.getTaskTemplate().getContainer().getMounts().add(new Mount(type, source,target,readOnly));
        return this;
    }

    public CmdServiceUpdate replicate(int replicas){
        if(serviceSpec.getMode().getReplicated()!=null) {
            Replicated replicated = serviceSpec.getMode().getReplicated();
            replicated.setReplicas(replicas);
        }
        return this;
    }

    public CmdServiceUpdate restartPolicy(long delay, String ondition, int maxAttempts){
        serviceSpec.getTaskTemplate().getRestartPolicy().setDelay(delay);
        serviceSpec.getTaskTemplate().getRestartPolicy().setCondition(ondition);
        serviceSpec.getTaskTemplate().getRestartPolicy().setMaxAttempts(maxAttempts);
        return this;
    }

    public CmdServiceUpdate updateConfig(long delay, String failureAction, int parallelism){
        serviceSpec.getUpdateConfig().setDelay(delay);
        serviceSpec.getUpdateConfig().setFailureAction(failureAction);
        serviceSpec.getUpdateConfig().setParallelism(parallelism);
        return this;
    }

    public CmdServiceUpdate label(String key, String value) {
        serviceSpec.getLabels().put(key, value);
        return this;
    }

    //v1.25
    public CmdServiceUpdate forceUpdate(Integer forceUpdate) {
        serviceSpec.getTaskTemplate().setForceUpdate(forceUpdate);
        return this;
    }

    //v1.25
    public CmdServiceUpdate tty(boolean tty){
        serviceSpec.getTaskTemplate().getContainer().setTty(tty);
        return this;
    }

    //v1.25
    public CmdServiceUpdate dnsNameservers(String  nameservers){
        serviceSpec.getTaskTemplate().getContainer().getDnsConfig()
                .getNameservers().addAll(TObject.asList(nameservers));
        return this;
    }

    //v1.25
    public CmdServiceUpdate dnsSearch(String  Search){
        serviceSpec.getTaskTemplate().getContainer().getDnsConfig()
                .getNameservers().addAll(TObject.asList(Search));
        return this;
    }

    //v1.25
    public CmdServiceUpdate dnsOptions(String  options){
        serviceSpec.getTaskTemplate().getContainer().getDnsConfig()
                .getNameservers().addAll(TObject.asList(options));
        return this;
    }

    public ServiceSpec getEntity(){
        return serviceSpec;
    }

    public static CmdServiceUpdate newInstance(String nameOrId, Integer version){
        return new CmdServiceUpdate(nameOrId, version);
    }

    @Override
    public String send() throws Exception {
        Result result = getDockerHttpClient().run("POST","/services/"+nameOrId+"/update",getParameters(),serviceSpec);
        if(result!=null && result.getStatus()>=300){
            throw new DockerClientException(result.getMessage());
        }else{
            return result.getMessage();
        }
    }
}
