package org.voovan.docker.message.network;

import org.voovan.docker.message.network.atom.IPAM;
import org.voovan.tools.json.JSON;
import org.voovan.tools.log.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author helyho
 * <p>
 * DockerFly Framework.
 * WebSite: https://git.oschina.net/helyho/JDocker
 * Licence: Apache v2 License
 */
public class NetworkCreate {
    private String name;
    private String driver;
    private Boolean checkDuplicate;
    private Boolean enableIPv6;
    private Boolean internal;
    private Boolean attachable;
    private IPAM ipam;
    private Map<String, Object> options;
    private Map<String, String> labels;

    public NetworkCreate() {
        options = new HashMap<String, Object>();
        labels = new HashMap<String, String>();
        ipam = new IPAM();
        enableIPv6 = false;
        internal = true;
        checkDuplicate = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Boolean isCheckDuplicate() {
        return checkDuplicate;
    }

    public void setCheckDuplicate(Boolean checkDuplicate) {
        this.checkDuplicate = checkDuplicate;
    }

    public Boolean isEnableIPv6() {
        return enableIPv6;
    }

    public void setEnableIPv6(Boolean enableIPv6) {
        this.enableIPv6 = enableIPv6;
    }

    public Boolean isInternal() {
        return internal;
    }

    public Boolean getAttachable() {
        return attachable;
    }

    public void setAttachable(Boolean attachable) {
        this.attachable = attachable;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public IPAM getIpam() {
        return ipam;
    }

    public void setIpam(IPAM ipam) {
        this.ipam = ipam;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public static void main(String[] args) {
        NetworkCreate networkCreate = new NetworkCreate();
        networkCreate.setName("prog_test");
        networkCreate.setDriver("bridge");
        networkCreate.getLabels().put("test", "dafdasdf");

        Logger.simple(JSON.formatJson(JSON.toJSON(networkCreate)));
    }
}
