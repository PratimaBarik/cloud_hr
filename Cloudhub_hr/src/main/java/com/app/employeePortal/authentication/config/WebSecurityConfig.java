package com.app.employeePortal.authentication.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/token/*").permitAll()
                .antMatchers("/api/v1/registration").permitAll()
                .antMatchers("/api/v1/emailValidation").permitAll()
                .antMatchers("/api/v1/forgotPassword").permitAll()
                .antMatchers("/api/v1/setPassword").permitAll()
//                .antMatchers("/api/v1/countries/{orgId}").permitAll()
//                .antMatchers("/api/v1/currencies/{orgId}").permitAll()
                .antMatchers("/api/v1/timezones").permitAll()
                .antMatchers("/api/v1/image/*").permitAll()
                .antMatchers("/api/v1/document/*").permitAll()
                .antMatchers("/api/v1/excel/export/user/*").permitAll()
                .antMatchers("/api/v1/excel/export/organization/*").permitAll()
                .antMatchers("/api/v1/excel/export/user/customer/*").permitAll()
                .antMatchers("/api/v1/excel/export/user/contact/*").permitAll()
                .antMatchers("/api/v1/excel/export/user/partner/*").permitAll()
                .antMatchers("/api/v1/excel/export/user/opportunity/*").permitAll()
                .antMatchers("/api/v1/excel/export/user/candidate/*").permitAll()
                .antMatchers("/api/v1/excel/export/user/employee/*").permitAll()
                .antMatchers("/api/v1/csv/export/user/contact/*").permitAll()
                .antMatchers("/api/v1/csv/export/user/*").permitAll()
                .antMatchers("/saveCurrency").permitAll()
                .antMatchers("/name").permitAll()
                .antMatchers("/api/v1/recruitment/website/publish/*").permitAll()
                .antMatchers("/api/v1/excel/export/recruitment/summary/*").permitAll()
                .antMatchers("/api/v1/recruitment/publish").permitAll()
                .antMatchers("/api/v1/recruitment/publish/website").permitAll()
                .antMatchers("/api/v1/candidate/website").permitAll()
                .antMatchers("/api/v1/sector/website").permitAll()
                .antMatchers("/api/v1/department/website").permitAll()
                .antMatchers("/api/v1/candidate/defination/website").permitAll()
                .antMatchers("/api/v1/idProofType/all-list/website").permitAll()
                .antMatchers("/api/v1/designation/website").permitAll()
                .antMatchers("/api/v1/roleType/website").permitAll()
                .antMatchers("/api/v1/currencies/website").permitAll()
                .antMatchers("/api/v1/countries/website").permitAll()
                .antMatchers("/api/v1/candidate/verify/email/website").permitAll()
                .antMatchers("/api/v1/partner/website").permitAll()
                .antMatchers("/api/v1/link/recriutment/candidate/website").permitAll()
                .antMatchers("/api/v1/candidate/save-add/process/website").permitAll()
                .antMatchers("/entries/{cacheName}").permitAll()
                .antMatchers("/api/v1/feedback/library").permitAll()
                .antMatchers("/api/v1/otp/generateOTP").permitAll()
                .antMatchers("/api/v1/otp/validateOtp").permitAll()
                .antMatchers("/api/v1/videoClips/*").permitAll()
                .antMatchers("/api/v1/videoClips/upload/website").permitAll()
                .antMatchers("/api/v1/document/website/upload").permitAll()
                .antMatchers("/api/v1/excel/export/recruitment/org/reports/{orgId}/{type}").permitAll()
                .antMatchers("/api/v1/excel/export/reports/user/sales/{userId}/{type}/{startDate}/{endDate}").permitAll()
                .antMatchers("/api/v1/report/scheduling/weekly").permitAll()
                .antMatchers("/api/v1/candidate/login/{candidateId}").permitAll()
                .antMatchers("/api/v1/candidate/{candidateId}/website").permitAll()
                .antMatchers("/api/v1/candidate/suggested/recruitment/{candidateId}").permitAll()
                .antMatchers("/api/v1/recriutment/web/project-name/{candidateId}").permitAll()
                .antMatchers("/api/v1/recriutment/web/project-name/customer/{candidateId}").permitAll()
                .antMatchers("/api/v1/employee/leave/website").permitAll()
                .antMatchers("/api/v1/employee/leave/update/website").permitAll()
                .antMatchers("/api/v1/employee/leaves/{employeeId}/website").permitAll()
                .antMatchers("/api/v1/candidate/{candidateId}/website").permitAll()
                .antMatchers("/api/v1/hour/user/table/website/{candidateId}").permitAll()
                .antMatchers("/api/v1/hour/user/planner/website/{candidateId}").permitAll()
                .antMatchers("/api/v1/hour/save/website").permitAll()
                .antMatchers("/api/v1/hour/candidate/task/{hourId}/website").permitAll()
                .antMatchers("/api/v1/recruit/dashboard/candidate/{candidateId}").permitAll()
                .antMatchers("/api/v1/task/candidate/web/{candidateId}").permitAll()
                .antMatchers("/api/v1/task/candidate/web/status/{candidateId}/{taskId}").permitAll()
                .antMatchers("/api/v1/aws/download/{fileName}").permitAll()
                .antMatchers("/api/v1/present/notifications/web/{userId}").permitAll()
                .antMatchers("/api/v1/previous/notifications/web/{userId}").permitAll()
                .antMatchers("/api/v1/task/comment/save/website").permitAll()
                .antMatchers("/api/v1/task/task-comment/all/list/website/{taskId}").permitAll()
                .antMatchers("/api/v1/hour/candidate/total/complete-unit/{candidateId}/website").permitAll()
                .antMatchers("/api/v1/hour/candidate/{candidateId}/{taskId}/website").permitAll()
                .antMatchers("/api/v1/hour/candidate/all/hour-list/{candidateId}/website").permitAll()
                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers("/api/v1/excel/export/user/location/{userId}").permitAll()
                .antMatchers("/api/v1/excel/export/user/leads/{userId}").permitAll()
                .antMatchers("/api/v1/excel/export/org/employee_list/{orgId}").permitAll()
                .antMatchers("/api/v1/excel/export/org/leads_list/{orgId}").permitAll()
                .antMatchers("/api/v1/excel/export/organisation/location/{orgId}").permitAll()
                .antMatchers("/api/v1/excel/export/mileage/organization/{organizationId}{status}").permitAll()
                .antMatchers("/api/v1/leads/website").permitAll()
                .antMatchers("api/v1/import").permitAll()
                .antMatchers("/api/v1/otp/user/generateOTP").permitAll()
                .antMatchers("/api/v1/otp/user/validateOtp").permitAll()
//                .antMatchers("/api/v1/currencies/sales/{orgId}").permitAll()
//                .antMatchers("/api/v1/currencies/investor/{orgId}").permitAll()
                .antMatchers("/api/v1/excel/export/catagory/All/{orgId}").permitAll()
                .antMatchers("/api/v1/excel/export/country/All").permitAll()
                .antMatchers("/api/v1/excel/export/currency/All").permitAll()
                .antMatchers("/api/v1/otp/user/link/one-to-another/generateOTP").permitAll()
                .antMatchers("/api/v1/otp/user/validateOtp/email/link").permitAll()
                .antMatchers("/api/v1/currency").permitAll()
                .antMatchers("/api/v1/migration/employeeTable").permitAll()
                .antMatchers("/api/v1/migration/customerTable").permitAll()
                .antMatchers("/api/v1/migration/investorTable").permitAll()
                .antMatchers("/api/v1/migration/contactDetailsTable").permitAll()
                .antMatchers("/api/v1/migration/leadsTable").permitAll()
                .antMatchers("/api/v1/migration/employeeSettingTable").permitAll()
                .antMatchers("/api/v1/migration/investorleadsTable").permitAll()
                .antMatchers("/api/v1/migration/opportunity").permitAll()
                .antMatchers("/api/v1/migration/investorOpportunity").permitAll()
                .antMatchers("/api/v1/razorpay/order").permitAll()
                .antMatchers("/api/v1/razorpay/verify").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
