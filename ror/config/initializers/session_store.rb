# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_ror_session',
  :secret      => '85c43a6a2e00290edf53cea0430a8db835aa6814655bc7ea4cc892120d8be3a26222c98af22bff22172de9a04c56b97ae5c601736f2c57b60c1a2a5d4d8e2d9d'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
