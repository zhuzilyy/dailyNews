package com.qianyi.dailynews.ui.Mine.bean;

public class MoneyDetailBean {
    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public MoneyDetailData getData() {
        return data;
    }

    public void setData(MoneyDetailData data) {
        this.data = data;
    }

    private String return_code;
    private String code;
    private String return_msg;
    private MoneyDetailData data;





    public class MoneyDetailData{
        public MoneyDetailInfo getMakeMoney() {
            return makeMoney;
        }

        public void setMakeMoney(MoneyDetailInfo makeMoney) {
            this.makeMoney = makeMoney;
        }

        private MoneyDetailInfo makeMoney;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        private String status;
        private String endTime;


        public class MoneyDetailInfo{
            private String id;
            private String title;
            private String url;
            private String exposition;
            private String logo;
            private String cash;
            private String type;
            private String lineOne;
            private String lineTwo;
            private String lineThree;
            private String lineFour;
            private String participantsNum;
            private String cycle;
            private String introduce;
            private String imgs;
            private String rewardsType;
            private String flag;
            private String timeLimit;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getExposition() {
                return exposition;
            }

            public void setExposition(String exposition) {
                this.exposition = exposition;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getCash() {
                return cash;
            }

            public void setCash(String cash) {
                this.cash = cash;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLineOne() {
                return lineOne;
            }

            public void setLineOne(String lineOne) {
                this.lineOne = lineOne;
            }

            public String getLineTwo() {
                return lineTwo;
            }

            public void setLineTwo(String lineTwo) {
                this.lineTwo = lineTwo;
            }

            public String getLineThree() {
                return lineThree;
            }

            public void setLineThree(String lineThree) {
                this.lineThree = lineThree;
            }

            public String getLineFour() {
                return lineFour;
            }

            public void setLineFour(String lineFour) {
                this.lineFour = lineFour;
            }

            public String getParticipantsNum() {
                return participantsNum;
            }

            public void setParticipantsNum(String participantsNum) {
                this.participantsNum = participantsNum;
            }

            public String getCycle() {
                return cycle;
            }

            public void setCycle(String cycle) {
                this.cycle = cycle;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getRewardsType() {
                return rewardsType;
            }

            public void setRewardsType(String rewardsType) {
                this.rewardsType = rewardsType;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getTimeLimit() {
                return timeLimit;
            }

            public void setTimeLimit(String timeLimit) {
                this.timeLimit = timeLimit;
            }
        }

    }

}
