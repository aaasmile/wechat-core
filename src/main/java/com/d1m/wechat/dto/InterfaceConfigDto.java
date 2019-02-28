package com.d1m.wechat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class InterfaceConfigDto {

    private String id;
    private Integer menuKey;
    private String brand;
    private String name;
    private int methodType;  //修改
    private int type;              //修改
    private String event;
    private String interfaceName;
    private String parameter;
    private String description;
    private String url;
    private String wrongUrl;
    private String key;
    private String secret;
    private String sequence;
    private String deleted;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
    @Getter
    @Setter
    private Boolean retry;
    private int status;        //修改

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(Integer menuKey) {
        this.menuKey = menuKey;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMethodType() {                  //修改
        return methodType;
    }

    public void setMethodType(int methodType) {    //修改
        this.methodType = methodType;                              //修改
    }

    public int getType() {
        return type;
    }                 //修改

    public void setType(int type) {
        this.type = type;
    }    //修改

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWrongUrl() {
        return wrongUrl;
    }

    public void setWrongUrl(String wrongUrl) {
        this.wrongUrl = wrongUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


    public int getStatus() {
        return status;
    }                      //修改

    public void setStatus(int status) {
        this.status = status;
    }       //修改

    public InterfaceConfigDto() {
        super();
    }

    public static class Builder {
        private String id;
        private Integer menuKey;
        private String brand;
        private String name;
        private int methodType;
        private int type;
        private String event;
        private String interfaceName;
        private String parameter;
        private String description;
        private String url;
        private String wrongUrl;
        private String key;
        private String secret;
        private String sequence;
        private String isDeleted;
        private String createdAt;
        private String createdBy;
        private String lasteUpdatedAt;
        private String lasteUpdatedBy;
        private int status;
        private Boolean retry;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder menuKey(Integer menuKey) {
            this.menuKey = menuKey;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder methodType(int methodType) {
            this.methodType = methodType;
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder event(String event) {
            this.event = event;
            return this;
        }

        public Builder interfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public Builder parameter(String parameter) {
            this.parameter = parameter;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder wrongUrl(String wrongUrl) {
            this.wrongUrl = wrongUrl;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder sequence(String sequence) {
            this.sequence = sequence;
            return this;
        }

        public Builder isDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder lasteUpdatedAt(String lasteUpdatedAt) {
            this.lasteUpdatedAt = lasteUpdatedAt;
            return this;
        }

        public Builder lasteUpdatedBy(String lasteUpdatedBy) {
            this.lasteUpdatedBy = lasteUpdatedBy;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }


        public Builder retry(Boolean retry) {
            this.retry = retry;
            return this;
        }


        public InterfaceConfigDto build() {
            return new InterfaceConfigDto(this);
        }
    }

    private InterfaceConfigDto(Builder builder) {
        this.id = builder.id;
        this.menuKey = builder.menuKey;
        this.brand = builder.brand;
        this.name = builder.name;
        this.methodType = builder.methodType;
        this.type = builder.type;
        this.event = builder.event;
        this.interfaceName = builder.interfaceName;
        this.parameter = builder.parameter;
        this.description = builder.description;
        this.url = builder.url;
        this.wrongUrl = builder.wrongUrl;
        this.key = builder.key;
        this.secret = builder.secret;
        this.sequence = builder.sequence;
        this.deleted = builder.isDeleted;
        this.createdAt = builder.createdAt;
        this.createdBy = builder.createdBy;
        this.updatedAt = builder.lasteUpdatedAt;
        this.updatedBy = builder.lasteUpdatedBy;
        this.status = builder.status;
        this.retry = builder.retry;
    }


    public InterfaceConfigDto(String id, String name, String interfaceName) {
        this.id = id;
        this.name = name;
        this.interfaceName = interfaceName;
    }

    @Override
    public String toString() {
        return "InterfaceConfigDto{" +
                "id='" + id + '\'' +
                ", menuKey=" + menuKey +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", methodType=" + methodType +
                ", type=" + type +
                ", event='" + event + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", parameter='" + parameter + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", wrongUrl='" + wrongUrl + '\'' +
                ", key='" + key + '\'' +
                ", secret='" + secret + '\'' +
                ", sequence='" + sequence + '\'' +
                ", deleted='" + deleted + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", retry=" + retry +
                ", status=" + status +
                '}';
    }
}
