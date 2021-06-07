package gov.census.maftigerstats.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.census.maftigerstats.entity.AuthenticationRequest;
import gov.census.maftigerstats.entity.AuthenticationResponse;
import gov.census.maftigerstats.entity.BrExecute;
import gov.census.maftigerstats.entity.BrImplcatDesc;
import gov.census.maftigerstats.entity.BrPending;
import gov.census.maftigerstats.entity.BrRule;
import gov.census.maftigerstats.entity.RefreshToken;
import gov.census.maftigerstats.entity.TokenRefreshRequest;
import gov.census.maftigerstats.service.MyUserDetailsService;
import gov.census.maftigerstats.service.RefreshTokenService;
import gov.census.maftigerstats.service.StatsService;
import gov.census.maftigerstats.util.JwtUtil;
import gov.census.maftigerstats.util.TokenRefreshException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class SumStatsRestController {

	@Autowired
	private StatsService statsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@GetMapping("/")
	public String index() {
		return "Welcome to the home page!";
	}

	@GetMapping("/testGet")
	public String testGet() {
		return "<h1>Welcome home!!</h1>";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = authentication != null ? (UserDetails) authentication.getPrincipal()
				: myUserDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		String accessToken = jwtTokenUtil.generateToken(userDetails);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails);
		List<String> roles = new ArrayList<String>();
		userDetails.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));
		return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken.getToken(), roles));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken);
		if (refreshToken != null && refreshTokenService.verifyExpiration(refreshToken)
				&& (refreshToken.getToken() != null || refreshToken.getToken() != "")) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = authentication != null ? (UserDetails) authentication.getPrincipal()
					: myUserDetailsService.loadUserByUsername(refreshToken.getUserName());
			String accessToken = jwtTokenUtil.generateToken(userDetails);
			List<String> roles = new ArrayList<String>();
			userDetails.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));
			return ResponseEntity.ok(new AuthenticationResponse(accessToken, requestRefreshToken, roles));
		} else {
			throw new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!");
		}
	}

// User story #148
	@GetMapping("/brRuleInfo")
	public List<BrRule> brRuleInfo(@RequestParam(required = false) String ruleid,
			@RequestParam(required = false) String rulecat, @RequestParam(required = false) String ruledesc,
			@RequestParam(required = false) String implcat, @RequestParam(required = false) String execute,
			@RequestParam(required = false) String feattab, @RequestParam(required = false) String relfeattab,
			@RequestParam(required = false) String crActive) {
		return statsService.getBrRuleInfo(ruleid, rulecat, ruledesc, implcat, execute, feattab, relfeattab, crActive);
	}

// User story #130
	@GetMapping("/brRuleInfoAll")
	public List<BrRule> brRuleInfo_all() {
		return statsService.getBrRuleInfo_All();
	}

// User story #148
	@GetMapping("/brRuleCat")
	public List<BrRule> brRuleCat() {
		return statsService.getRuleCategory();
	}

// User story #148
	@GetMapping("/brFeatTab")
	public List<BrRule> brFeatTab() {
		return statsService.getFeatTab();
	}

// User story #148
	@GetMapping("/brRelFeatTab")
	public List<BrRule> brRelFeatTab() {
		return statsService.getRelFeatTab();
	}

// User story #148
	@GetMapping("/brImplcatDesc")
	public List<BrImplcatDesc> brImplcatDesc() {
		return statsService.getImplcatDesc();
	}

// User story #149
	@GetMapping("/brRuleDesc")
	public List<BrRule> brRuleDesc(@RequestParam String ruleDesc) {
		return statsService.getRuleDesc(ruleDesc);
	}

// User story #158
	@PostMapping("/brPending")
	public List brPending(@RequestBody BrPending brPending) {
		return statsService.insertBrPending(brPending);
	}

// User story #158
	@GetMapping("/brPending")
	public List<BrPending> brPending() {
		return statsService.getBrPending();
	}

// User story #162
	@PutMapping("/brPending/{brRuleId}")
	public List brPending(@PathVariable int brRuleId, @RequestParam String brSubmitter) {
		return statsService.updateBrPending(brRuleId, brSubmitter);
	}

// User story #163
	@GetMapping("/brPendingNC")
	public List<BrPending> brPendingNC() {
		return statsService.getBrPendingNC();
	}

// User story #163
	@GetMapping("/getUserInfo")
	public List<String> getUserInfo(HttpServletRequest req) {
		List<String> t = new ArrayList<String>();
		String temp = req.getRemoteUser();
		t.add(temp);
		System.out.println(temp);
		return t;
	}

// User story #186
	@PostMapping("/brExecute")
	public List brExecute(@RequestBody BrExecute brExecute) {
		return statsService.insertbrExecute(brExecute);
	}

// User story #193
	@GetMapping("/brExecute")
	public List<BrExecute> brExecute() {
		return statsService.getBrExecute();
	}
}