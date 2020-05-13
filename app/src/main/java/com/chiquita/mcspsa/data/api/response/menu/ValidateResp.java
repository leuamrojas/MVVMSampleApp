package com.chiquita.mcspsa.data.api.response.menu;

import com.chiquita.mcspsa.data.api.response.CoreCommonResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsTypeResp;

public class ValidateResp extends CoreCommonResp<ValidateResp.ValidateRowsResp, ValidateResp.ValidateRowsTypeResp> {

    public static class ValidateRowsResp extends CoreCommonRowsResp {

        public ValidateRowsResp() {
        }
    }

    public static class ValidateRowsTypeResp extends CoreCommonRowsTypeResp {

        public ValidateRowsTypeResp() {
        }
    }
}
