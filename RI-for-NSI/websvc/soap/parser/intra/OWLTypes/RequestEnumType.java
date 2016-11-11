/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.OWLTypes;

/**
 *
 * @author TSOC
 */
public enum RequestEnumType {
    rsv_rq, rsvabort_rq, rsvcommit_rq, rsv_cf, rsv_fl, rsvabort_cf,
    rsvcommit_cf, rsvcommit_fl, rsv_timeout, prov_rq, rel_rq, prov_cf,
    rel_cf, term_rq, term_cf, forced_end,
    
    LIFECYCLE_STATE_PASSED_END_TIME
}
