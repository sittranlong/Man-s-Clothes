export class PageConfig {
    public id: string;
    public name: string;
    public code: string;
    public value: string;
    public description: string;
    public status: string;
    public type: string;
    public locale: string;
}

export const PAGE_CONFIG_TYPE = {
    ABOUT_PAGE: {text: 'Về chúng tôi', value: 'about_page'},
    CONTACT_PAGE: {text: 'Liên hệ', value: 'contact_page'},
    FAQ_PAGE: {text: 'FAQ', value: 'faq_page'},
}

export const PAGE_CONFIG_TYPE_LIST = [
    PAGE_CONFIG_TYPE.ABOUT_PAGE,
    PAGE_CONFIG_TYPE.CONTACT_PAGE,
    PAGE_CONFIG_TYPE.FAQ_PAGE,
]

export const LOCALE_CONSTANT = {
    VI: {text: 'Tiếng Việt', value: 'vi_VN'},
    EN: {text: 'English', value: 'en_US'},
    ZH: {text: '中文', value: 'zh'},
}

export const LOCALE_CONSTANT_LIST = [
    LOCALE_CONSTANT.VI,
    LOCALE_CONSTANT.EN,
    LOCALE_CONSTANT.ZH,
]