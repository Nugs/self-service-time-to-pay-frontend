package uk.gov.hmrc.ssttp.config;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.time.FastDateFormat;
import play.api.*;
import play.api.mvc.*;
import play.twirl.api.Html;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.JavaConversions;
import scala.collection.Seq;
import scala.concurrent.Future;
import uk.gov.hmrc.play.audit.filters.FrontendAuditFilter;
import uk.gov.hmrc.play.audit.filters.FrontendAuditFilter$class;
import uk.gov.hmrc.play.audit.http.config.AuditingConfig;
import uk.gov.hmrc.play.audit.http.config.LoadAuditingConfig;
import uk.gov.hmrc.play.audit.http.connector.AuditConnector;
import uk.gov.hmrc.play.config.AppName$;
import uk.gov.hmrc.play.config.ControllerConfig;
import uk.gov.hmrc.play.frontend.bootstrap.DefaultFrontendGlobal;
import uk.gov.hmrc.play.http.HeaderCarrier;
import uk.gov.hmrc.play.http.logging.filters.FrontendLoggingFilter;
import uk.gov.hmrc.play.http.logging.filters.FrontendLoggingFilter$class;
import uk.gov.hmrc.play.http.logging.filters.LoggingFilter$class;

public class Global extends DefaultFrontendGlobal {
    @Override
    public FrontendLoggingFilter loggingFilter() {
        return new FrontendLoggingFilter() {
            public boolean uk$gov$hmrc$play$http$logging$filters$LoggingFilter$_setter_$uk$gov$hmrc$play$http$logging$filters$LoggingFilter$$dateFormat_$eq(FastDateFormat format) {
                return true;
            }

            public FastDateFormat uk$gov$hmrc$play$http$logging$filters$LoggingFilter$$dateFormat() {
                return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSSZZ");
            }

            @Override
            public EssentialAction apply(EssentialAction next) {
                return Filter$class.apply(this, next);
            }

            @Override
            public HeaderCarrier buildLoggedHeaders(RequestHeader request) {
                return FrontendLoggingFilter$class.buildLoggedHeaders(this, request);
            }

            @Override
            public Future<Result> apply(Function1<RequestHeader, Future<Result>> next, RequestHeader rh) {
                return LoggingFilter$class.apply(this, next, rh);
            }

            @Override
            public boolean controllerNeedsLogging(String controllerName) {
                return ((ControllerConfig)Play.current().configuration().underlying().getConfig("controllers")).paramsForController(controllerName).needsLogging();
            }

            @Override
            public LoggerLike logger() {
                return LoggingFilter$class.logger(this);
            }
        };
    }

    @Override
    public FrontendAuditFilter frontendAuditFilter() {
        return new FrontendAuditFilter() {
            @Override
            public Seq<String> maskedFormFields() {
                return JavaConversions.collectionAsScalaIterable(Sets.newHashSet("password")).toSeq();
            }

            @Override
            public Option<Object> applicationPort() {
                return None$.MODULE$;
            }

            @Override
            public AuditConnector auditConnector() {
                return auditConnector;
            }

            @Override
            public boolean controllerNeedsAuditing(String controllerName) {
                return ((ControllerConfig)Play.current().configuration().underlying().getConfig("controllers")).paramsForController(controllerName).needsAuditing();
            }

            @Override
            public String appName() {
                return AppName$.MODULE$.appName();
            }
        };
    }

    @Override
    public Option<Configuration> microserviceMetricsConfig(Application app) {
        return app.configuration().getConfig("microservice.metrics");
    }

    @Override
    public Html standardErrorTemplate(String pageTitle, String heading, String message, Request<?> request) {
        return null;
    }

    private AuditConnector auditConnector = new AuditConnector() {
        @Override
        public Logger$ logger() {
            return Logger$.MODULE$;
        }

        @Override
        public Logger connectionLogger() {
            return Logger.apply("connector");
        }

        @Override
        public AuditingConfig auditingConfig() {
            return LoadAuditingConfig.apply("auditing");
        }
    };

    @Override
    public AuditConnector auditConnector() {
        return auditConnector;
    }
}
