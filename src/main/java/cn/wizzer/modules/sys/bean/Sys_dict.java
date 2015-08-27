package cn.wizzer.modules.sys.bean;

import cn.wizzer.common.service.core.BasePojo;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

/**
 * Created by Wizzer.cn on 2015/7/1.
 */
@Table("sys_dict")
public class Sys_dict extends BasePojo implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Column
    @Name
	private String id;
	@Column
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String dkey;
	@Column
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String dval;
	@Column
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String txt;
	@Column
    @ColDefine(type = ColType.INT, width = 1)
    private int status;
	@Column
	private int location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDkey()
	{
		return dkey;
	}
	public void setDkey(String dkey)
	{
		this.dkey=dkey;
	}
	public String getDval()
	{
		return dval;
	}
	public void setDval(String dval)
	{
		this.dval=dval;
	}
	public String getTxt()
	{
		return txt;
	}
	public void setTxt(String txt)
	{
		this.txt=txt;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status=status;
	}
	public int getLocation()
	{
		return location;
	}
	public void setLocation(int location)
	{
		this.location=location;
	}

}