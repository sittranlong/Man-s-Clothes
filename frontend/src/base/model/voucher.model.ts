export class Voucher {
    public static readonly DISCOUNT_TYPE_PERCENT = "percent"
    public static readonly DISCOUNT_TYPE_MONEY = "money"
    id?: string;
    name?: string;
    code?: string;
    discount?: number;
    minTotal?: number;
    maxDiscount?: number;
    maxUsage?: number;
    usage?: number;
    status?: string;
    startDate?: number;
    endDate?: number;
    description?: string;
    type?: string; // 1: percent, 2: money
}

export const VOUCHER_TYPE = {
    DISCOUNT_TYPE_PERCENT: {value: "percent", text: 'Phần trăm'},
    DISCOUNT_TYPE_MONEY: {value: "money", text: 'Số tiền'},
}

export const VOUCHER_STATUS = {
    ACTIVE: {value: "ACTIVE", text: 'Hoạt động'},
    INACTIVE: {value: "INACTIVE", text: 'Không hoạt động'},

}

export const VOUCHER_STATUS_LIST = [
    VOUCHER_STATUS.ACTIVE,
    VOUCHER_STATUS.INACTIVE
];

export const VOUCHER_TYPE_LIST = [
    VOUCHER_TYPE.DISCOUNT_TYPE_PERCENT,
    VOUCHER_TYPE.DISCOUNT_TYPE_MONEY
];