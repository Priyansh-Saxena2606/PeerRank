function StatCard({ title, value, icon }) {
    return (
        <div className="group rounded-3xl border border-slate-800 bg-gradient-to-br from-slate-900 to-slate-950 p-6 transition-all duration-300 hover:-translate-y-2 hover:border-violet-500">

            <div className="mb-8 flex h-14 w-14 items-center justify-center rounded-2xl bg-violet-500/10 text-violet-400 transition-all group-hover:scale-110">
                {icon}
            </div>

            <p className="text-slate-400">
                {title}
            </p>

            <h2 className="mt-2 text-5xl font-semibold">
                {value}
            </h2>

        </div>
    );
    
}

export default StatCard;