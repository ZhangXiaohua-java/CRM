package cloud.huel.crm.workbench.web.domain;

public class ActivityRemark {
    public static final String HAS_EDITED = "1";
    public static final String NON_EDITED = "0";
    private String id;

    private String noteContent;

    private String createTime;

    private String createBy;

    private String editTime;

    private String editBy;

    private String editFlag;

    private String activityId;

    private Activity activity;

    public ActivityRemark(String id, String noteContent, String createTime, String createBy, String editTime, String editBy, String editFlag, String activityId) {
        this.id = id;
        this.noteContent = noteContent;
        this.createTime = createTime;
        this.createBy = createBy;
        this.editTime = editTime;
        this.editBy = editBy;
        this.editFlag = editFlag;
        this.activityId = activityId;
    }

    public ActivityRemark() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent == null ? null : noteContent.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime == null ? null : editTime.trim();
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy == null ? null : editBy.trim();
    }

    public String getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag == null ? null : editFlag.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "ActivityRemark{" +
                "id='" + id + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", editTime='" + editTime + '\'' +
                ", editBy='" + editBy + '\'' +
                ", editFlag='" + editFlag + '\'' +
                ", activityId='" + activityId + '\'' +
                ", activity=" + activity +
                '}';
    }
}