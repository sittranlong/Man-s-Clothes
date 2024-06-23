export class Voucher {
    static readonly DISCOUNT_TYPE_PERCENT = 1;
    static readonly DISCOUNT_TYPE_MONEY = 2;
    id?: string;
    code?: string;
    discountType: number = Voucher.DISCOUNT_TYPE_MONEY;
    discountValue: number = 0;
    startDate?: Date;
    endDate?: Date;
    quantity: number = 0;
}

export const VOUCHER_TYPE = [
    {value: Voucher.DISCOUNT_TYPE_PERCENT, text: 'Phần trăm'},
    {value: Voucher.DISCOUNT_TYPE_MONEY, text: 'Số tiền'},
];