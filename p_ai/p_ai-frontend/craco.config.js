// craco.config.js
module.exports = {
  webpack: {
    configure: (webpackConfig, { env }) => {
      if (env === "production") {
        // CssMinimizerPlugin을 제거해서 CSS 최소화 단계 건너뛰기
        webpackConfig.optimization.minimizer =
          webpackConfig.optimization.minimizer.filter(
            (plugin) => plugin.constructor.name !== "CssMinimizerPlugin"
          );
      }
      return webpackConfig;
    },
  },
};
