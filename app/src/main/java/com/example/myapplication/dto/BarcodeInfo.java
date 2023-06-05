package com.example.myapplication.dto;

import java.util.List;

public class BarcodeInfo {
    private Info barcodeInfo;

    public BarcodeInfo(Info barcodeInfo) {
        this.barcodeInfo = barcodeInfo;
    }

    public Info getBarcodeInfo() {
        return barcodeInfo;
    }

    public static class Info {
        private Integer total_count;
        private List<Data> row;

        public Info(Integer total_count, List<Data> row) {
            this.total_count = total_count;
            this.row = row;
        }

        public Integer getTotal_count() {
            return total_count;
        }

        public List<Data> getRow() {
            return row;
        }

        public static class Data {
            private String PRDLST_REPORT_NO;         // 품목신고번호
            private String PRMS_DT;                  // 보고일
            private String PRDLST_NM;                // 제품명
            private String POG_DAYCNT;               // 유통/소비기한
            private String PRDLST_DCNM;              // 식품 유형
            private String BSSH_NM;                  // 제조사명
            private String INDUTY_NM;                // 업종
            private String SITE_ADDR;                // 주소
            private String CLSBIZ_DT;                // 폐업일자
            private String BAR_CD;                   // 유통바코드

            public Data(String PRDLST_REPORT_NO, String PRMS_DT, String PRDLST_NM, String POG_DAYCNT, String PRDLST_DCNM, String BSSH_NM, String INDUTY_NM, String SITE_ADDR, String CLSBIZ_DT, String BAR_CD) {
                this.PRDLST_REPORT_NO = PRDLST_REPORT_NO;
                this.PRMS_DT = PRMS_DT;
                this.PRDLST_NM = PRDLST_NM;
                this.POG_DAYCNT = POG_DAYCNT;
                this.PRDLST_DCNM = PRDLST_DCNM;
                this.BSSH_NM = BSSH_NM;
                this.INDUTY_NM = INDUTY_NM;
                this.SITE_ADDR = SITE_ADDR;
                this.CLSBIZ_DT = CLSBIZ_DT;
                this.BAR_CD = BAR_CD;
            }

            public String getPRDLST_REPORT_NO() {
                return PRDLST_REPORT_NO;
            }

            public String getPRMS_DT() {
                return PRMS_DT;
            }

            public String getPRDLST_NM() {
                return PRDLST_NM;
            }

            public String getPOG_DAYCNT() {
                return POG_DAYCNT;
            }

            public String getPRDLST_DCNM() {
                return PRDLST_DCNM;
            }

            public String getBSSH_NM() {
                return BSSH_NM;
            }

            public String getINDUTY_NM() {
                return INDUTY_NM;
            }

            public String getSITE_ADDR() {
                return SITE_ADDR;
            }

            public String getCLSBIZ_DT() {
                return CLSBIZ_DT;
            }

            public String getBAR_CD() {
                return BAR_CD;
            }

            @Override
            public String toString() {
                return "BarcodeInfo{" +
                        "PRDLST_REPORT_NO='" + PRDLST_REPORT_NO + '\'' +
                        ", PRMS_DT='" + PRMS_DT + '\'' +
                        ", PRDLST_NM='" + PRDLST_NM + '\'' +
                        ", POG_DAYCNT='" + POG_DAYCNT + '\'' +
                        ", PRDLST_DCNM='" + PRDLST_DCNM + '\'' +
                        ", BSSH_NM='" + BSSH_NM + '\'' +
                        ", INDUTY_NM='" + INDUTY_NM + '\'' +
                        ", SITE_ADDR='" + SITE_ADDR + '\'' +
                        ", CLSBIZ_DT='" + CLSBIZ_DT + '\'' +
                        ", BAR_CD='" + BAR_CD + '\'' +
                        '}';
            }
        }
    }
}



