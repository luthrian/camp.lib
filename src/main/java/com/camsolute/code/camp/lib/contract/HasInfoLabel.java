package com.camsolute.code.camp.lib.contract;

public interface HasInfoLabel {
	public static enum LabelFormat {
		STRING,
		JSON,
		HTML
	};
	public String infoLabel(LabelFormat format);
}
