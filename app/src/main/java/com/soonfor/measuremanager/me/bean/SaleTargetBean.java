package com.soonfor.measuremanager.me.bean;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class SaleTargetBean {

        /**
         * periodType : 1
         * personalTarget : {"baseActual":200000,"baseTarget":300000,"baseRate":0.66,"excellentActual":300000,"excellentTarget":500000,"excellentRate":0.6}
         * storeTarget : {"baseActual":600000,"baseTarget":900000,"baseRate":0.66,"excellentActual":900000,"excellentTarget":1500000,"excellentRate":0.6}
         */

        private int periodType;
        private PersonalTargetBean personalTarget;
        private StoreTargetBean storeTarget;

        public int getPeriodType() {
            return periodType;
        }

        public void setPeriodType(int periodType) {
            this.periodType = periodType;
        }

        public PersonalTargetBean getPersonalTarget() {
            return personalTarget;
        }

        public void setPersonalTarget(PersonalTargetBean personalTarget) {
            this.personalTarget = personalTarget;
        }

        public StoreTargetBean getStoreTarget() {
            return storeTarget;
        }

        public void setStoreTarget(StoreTargetBean storeTarget) {
            this.storeTarget = storeTarget;
        }

        public static class PersonalTargetBean {
            /**
             * baseActual : 200000  //实际完成的目标
             * baseTarget : 300000 //计划完成的目标
             * baseRate : 0.66 //完成的比率
             * excellentActual : 300000 //实际完成的卓越目标
             * excellentTarget : 500000 //计划完成的卓越目标
             * excellentRate : 0.6 //卓越目标的完成比率
             */

            private int baseActual;
            private int baseTarget;
            private double baseRate;
            private int excellentActual;
            private int excellentTarget;
            private double excellentRate;

            public int getBaseActual() {
                return baseActual;
            }

            public void setBaseActual(int baseActual) {
                this.baseActual = baseActual;
            }

            public int getBaseTarget() {
                return baseTarget;
            }

            public void setBaseTarget(int baseTarget) {
                this.baseTarget = baseTarget;
            }

            public double getBaseRate() {
                return baseRate;
            }

            public void setBaseRate(double baseRate) {
                this.baseRate = baseRate;
            }

            public int getExcellentActual() {
                return excellentActual;
            }

            public void setExcellentActual(int excellentActual) {
                this.excellentActual = excellentActual;
            }

            public int getExcellentTarget() {
                return excellentTarget;
            }

            public void setExcellentTarget(int excellentTarget) {
                this.excellentTarget = excellentTarget;
            }

            public double getExcellentRate() {
                return excellentRate;
            }

            public void setExcellentRate(double excellentRate) {
                this.excellentRate = excellentRate;
            }
        }

        public static class StoreTargetBean {
            /**
             * baseActual : 600000
             * baseTarget : 900000
             * baseRate : 0.66
             * excellentActual : 900000
             * excellentTarget : 1500000
             * excellentRate : 0.6
             *
             * "actualValue":4.21,
             *         "baseTarget":200,
             *         "baseRate":2.1,
             *         "goodTarget":220,
             *         "goodRate":1.9,
             *         "excellentTarget":240,
             *         "excellentRate":1.8
             */

            private int baseActual;
            private int baseTarget;
            private double baseRate;
            private int excellentActual;
            private int excellentTarget;
            private double excellentRate;

            public int getBaseActual() {
                return baseActual;
            }

            public void setBaseActual(int baseActual) {
                this.baseActual = baseActual;
            }

            public int getBaseTarget() {
                return baseTarget;
            }

            public void setBaseTarget(int baseTarget) {
                this.baseTarget = baseTarget;
            }

            public double getBaseRate() {
                return baseRate;
            }

            public void setBaseRate(double baseRate) {
                this.baseRate = baseRate;
            }

            public int getExcellentActual() {
                return excellentActual;
            }

            public void setExcellentActual(int excellentActual) {
                this.excellentActual = excellentActual;
            }

            public int getExcellentTarget() {
                return excellentTarget;
            }

            public void setExcellentTarget(int excellentTarget) {
                this.excellentTarget = excellentTarget;
            }

            public double getExcellentRate() {
                return excellentRate;
            }

            public void setExcellentRate(double excellentRate) {
                this.excellentRate = excellentRate;
            }
        }
}
