package com.canoo.dolphin.integration.runlater;

import com.canoo.platform.remoting.DolphinBean;
import com.canoo.platform.remoting.Property;

@DolphinBean
public class RunLaterTestBean {

    private Property<Integer> postConstructPreRunLaterCallIndex;

    private Property<Integer> postConstructRunLaterCallIndex;

    private Property<Integer> postConstructPostRunLaterCallIndex;


    private Property<Integer> actionPreRunLaterCallIndex;

    private Property<Integer> actionRunLaterCallIndex;

    private Property<Integer> actionPostRunLaterCallIndex;

    private Property<Integer> actionPreRunLaterAsyncCallIndex;

    private Property<Integer> actionRunLaterAsyncCallIndex;

    private Property<Integer> actionPostRunLaterAsyncCallIndex;

    public Integer getPostConstructPreRunLaterCallIndex() {
        return postConstructPreRunLaterCallIndex.get();
    }

    public Property<Integer> postConstructPreRunLaterCallIndexProperty() {
        return postConstructPreRunLaterCallIndex;
    }

    public void setPostConstructPreRunLaterCallIndex(Integer postConstructPreRunLaterCallIndex) {
        this.postConstructPreRunLaterCallIndex.set(postConstructPreRunLaterCallIndex);
    }

    public Integer getPostConstructRunLaterCallIndex() {
        return postConstructRunLaterCallIndex.get();
    }

    public Property<Integer> postConstructRunLaterCallIndexProperty() {
        return postConstructRunLaterCallIndex;
    }

    public void setPostConstructRunLaterCallIndex(Integer postConstructRunLaterCallIndex) {
        this.postConstructRunLaterCallIndex.set(postConstructRunLaterCallIndex);
    }

    public Integer getPostConstructPostRunLaterCallIndex() {
        return postConstructPostRunLaterCallIndex.get();
    }

    public Property<Integer> postConstructPostRunLaterCallIndexProperty() {
        return postConstructPostRunLaterCallIndex;
    }

    public void setPostConstructPostRunLaterCallIndex(Integer postConstructPostRunLaterCallIndex) {
        this.postConstructPostRunLaterCallIndex.set(postConstructPostRunLaterCallIndex);
    }

    public Integer getActionPreRunLaterCallIndex() {
        return actionPreRunLaterCallIndex.get();
    }

    public Property<Integer> actionPreRunLaterCallIndexProperty() {
        return actionPreRunLaterCallIndex;
    }

    public void setActionPreRunLaterCallIndex(Integer actionPreRunLaterCallIndex) {
        this.actionPreRunLaterCallIndex.set(actionPreRunLaterCallIndex);
    }

    public Integer getActionRunLaterCallIndex() {
        return actionRunLaterCallIndex.get();
    }

    public Property<Integer> actionRunLaterCallIndexProperty() {
        return actionRunLaterCallIndex;
    }

    public void setActionRunLaterCallIndex(Integer actionRunLaterCallIndex) {
        this.actionRunLaterCallIndex.set(actionRunLaterCallIndex);
    }

    public Integer getActionPostRunLaterCallIndex() {
        return actionPostRunLaterCallIndex.get();
    }

    public Property<Integer> actionPostRunLaterCallIndexProperty() {
        return actionPostRunLaterCallIndex;
    }

    public void setActionPostRunLaterCallIndex(Integer actionPostRunLaterCallIndex) {
        this.actionPostRunLaterCallIndex.set(actionPostRunLaterCallIndex);
    }

    public Integer getActionPreRunLaterAsyncCallIndex() {
        return actionPreRunLaterAsyncCallIndex.get();
    }

    public Property<Integer> actionPreRunLaterAsyncCallIndexProperty() {
        return actionPreRunLaterAsyncCallIndex;
    }

    public void setActionPreRunLaterAsyncCallIndex(Integer actionPreRunLaterAsyncCallIndex) {
        this.actionPreRunLaterAsyncCallIndex.set(actionPreRunLaterAsyncCallIndex);
    }

    public Integer getActionRunLaterAsyncCallIndex() {
        return actionRunLaterAsyncCallIndex.get();
    }

    public Property<Integer> actionRunLaterAsyncCallIndexProperty() {
        return actionRunLaterAsyncCallIndex;
    }

    public void setActionRunLaterAsyncCallIndex(Integer actionRunLaterAsyncCallIndex) {
        this.actionRunLaterAsyncCallIndex.set(actionRunLaterAsyncCallIndex);
    }

    public Integer getActionPostRunLaterAsyncCallIndex() {
        return actionPostRunLaterAsyncCallIndex.get();
    }

    public Property<Integer> actionPostRunLaterAsyncCallIndexProperty() {
        return actionPostRunLaterAsyncCallIndex;
    }

    public void setActionPostRunLaterAsyncCallIndex(Integer actionPostRunLaterAsyncCallIndex) {
        this.actionPostRunLaterAsyncCallIndex.set(actionPostRunLaterAsyncCallIndex);
    }
}
